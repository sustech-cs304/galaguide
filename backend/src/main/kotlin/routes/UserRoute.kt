package galaGuide.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.*
import galaGuide.data.user.asPrivateDetail
import galaGuide.data.user.asPrivateResponse
import galaGuide.data.user.asPublicResponse
import galaGuide.table.staticAsset
import galaGuide.table.user.User
import galaGuide.table.user.UserRole
import galaGuide.table.user.UserSignIn
import galaGuide.table.user.UserTable
import galaGuide.util.SMTP
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import io.ktor.util.pipeline.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun Route.routeUser() {
    val secret = environment?.config?.property("user-jwt.secret")?.getString()
        ?: throw IllegalArgumentException("user-jwt.secret not found")
    val issuer = environment?.config?.property("user-jwt.issuer")?.getString()
        ?: throw IllegalArgumentException("user-jwt.issuer not found")
    val audience = environment?.config?.property("user-jwt.audience")?.getString()
        ?: throw IllegalArgumentException("user-jwt.audience not found")

    fun generateToken(id: Long) = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("id", id)
        .withExpiresAt(Instant.now() + 30.days.toJavaDuration())
        .sign(Algorithm.HMAC256(secret))

    route("/user") {
        @Serializable
        data class LoginData(
            val token: String,
            val userName: String,
            val userRole: UserRole,
        )

        @Serializable
        data class RegisterRequest(val name: String = "", val password: String = "", val email: String = "")
        post<RegisterRequest>("/register") {
            newSuspendedTransaction {
                User.checkNameAvailable(it.name)
            }.takeIf { it } ?: run {
                call.respond(failRestResponseDefault(-1, "user already exists"))
                return@post
            }

            if (!User.checkEmailAvailable(it.email)) {
                call.respond(failRestResponseDefault(-2, "invalid email"))
                return@post
            }

            if (!User.checkPasswordAvailable(it.password)) {
                call.respond(failRestResponseDefault(-3, "password length should be 6-128"))
                return@post
            }

            val user = transaction {
                User.new {
                    name = it.name
                    changePassword(it.password)
                    email = it.email
                }
            }

            call.respond(
                LoginData(
                    generateToken(user.id.value),
                    user.name,
                    user.role,
                ).asRestResponse()
            )
        }

        @Serializable
        data class LoginRequest(val nameOrEmail: String = "", val password: String = "")
        post<LoginRequest>("/login") {
            val user = transaction {
                User.find {
                    (UserTable.name eq it.nameOrEmail) or (UserTable.email eq it.nameOrEmail)
                }.firstOrNull()
            } ?: run {
                call.respond(failRestResponseDefault(-1, "user not found"))
                return@post
            }

            if (!user.checkPassword(it.password)) {
                call.respond(failRestResponseDefault(-1, "password incorrect"))
                return@post
            }

            call.respond(
                LoginData(
                    generateToken(user.id.value),
                    user.name,
                    user.role,
                ).asRestResponse()
            )
        }

        authenticate("admin") {
            get("/all") {
                val option = call.receive<PagingOption>()
                val result = User.page(option) { it.asPrivateDetail() }
                call.respond(result.asRestResponse())
            }
        }

        authenticate("unverified") {
            val requestInterval = environment?.config?.property("user.email-verify.request-interval")?.getString()
                ?.toLongOrNull()?.seconds ?: 1.minutes
            val codeExpireTime =
                environment?.config?.property("user.email-verify.expire-time")?.getString()?.toLongOrNull()?.seconds
                    ?: 1.hours

            data class EmailVerifyInfo(
                val code: String,
                val nextRequestTime: kotlinx.datetime.Instant = Clock.System.now() + requestInterval,
                val expireTime: kotlinx.datetime.Instant = Clock.System.now() + codeExpireTime,
            )

            val emailVerifyMap = mutableMapOf<Long, EmailVerifyInfo>()
            val emailLogger = SMTP.logger

            post("/request-email") {
                val user = call.user!!

                if ((emailVerifyMap[call.userId]?.nextRequestTime
                        ?: kotlinx.datetime.Instant.DISTANT_PAST) > Clock.System.now()
                ) {
                    call.respond(failRestResponseDefault(-2, "request too frequent"))
                    return@post
                }

                val code = (1..6).joinToString("") {
                    (0..9).random().toString()
                }
                kotlin.runCatching {
                    SMTP.sendEmailVerification(user.email, user.name, code)
                }.onFailure {
                    emailLogger.error(it)
                    call.respond(failRestResponseDefault(-3, "send email failed"))
                }.onSuccess {
                    emailVerifyMap[call.userId!!] = EmailVerifyInfo(code)
                    call.respond(emptyRestResponse("code sent"))
                }
            }

            @Serializable
            data class EmailVerifyRequest(val code: String)
            post<EmailVerifyRequest>("/verify-email") {
                val user = call.user!!

                val info = emailVerifyMap[call.userId] ?: run {
                    call.respond(failRestResponseDefault(-2, "incorrect code"))
                    return@post
                }

                if (info.expireTime < Clock.System.now() || it.code != info.code) {
                    call.respond(failRestResponseDefault(-2, "incorrect code"))
                    return@post
                }

                transaction {
                    user.emailVerified = true
                }
                emailVerifyMap.remove(call.userId)
                call.respond(emptyRestResponse("email verified"))
            }
        }

        authenticate("user") {
            get {
                newSuspendedTransaction {
                    call.respond(call.user!!.asPrivateResponse())
                }
            }

            post("sign-in") {
                newSuspendedTransaction {
                    val signIn = UserSignIn[call.userId!!]
                    call.respond(signIn.signIn().asRestResponse())
                }
            }

            @Serializable
            data class UserModifyRequest(
                val name: String? = null,
                val email: String? = null,
                val avatarId: String? = null,
                val backgroundId: String? = null,
                val intro: String? = null,
                val role: UserRole? = null,
            )

            suspend fun PipelineContext<*, ApplicationCall>.editUserInfo(userId: Long, option: UserModifyRequest) {
                newSuspendedTransaction {
                    option.name?.let {
                        if (!User.checkNameAvailable(it)) {
                            call.respond(failRestResponseDefault(-1, "name already exists"))
                            return@newSuspendedTransaction
                        }
                    }

                    option.email?.let {
                        if (!User.checkEmailAvailable(it)) {
                            call.respond(failRestResponseDefault(-2, "invalid email"))
                            return@newSuspendedTransaction
                        }
                    }

                    val avatar = option.avatarId?.let {
                        it.staticAsset ?: run {
                            call.respond(failRestResponseDefault(-3, "avatar not found"))
                            return@newSuspendedTransaction
                        }
                    }

                    val background = option.backgroundId?.let {
                        it.staticAsset ?: run {
                            call.respond(failRestResponseDefault(-4, "background not found"))
                            return@newSuspendedTransaction
                        }
                    }

                    if (option.role != null && call.user!!.role != UserRole.ADMIN) {
                        call.respond(failRestResponseDefault(-5, "permission denied"))
                        return@newSuspendedTransaction
                    }

                    val user = User.findById(userId) ?: run {
                        call.respond(failRestResponseDefault(-6, "user not found"))
                        return@newSuspendedTransaction
                    }

                    option.name?.let { user.name = it }
                    option.email?.let {
                        user.email = it
                        user.emailVerified = false
                    }
                    avatar?.let { user.avatar = it }
                    background?.let { user.background = it }
                    option.intro?.let { user.intro = it }
                    option.role?.let { user.role = it }
                }
            }

            post<UserModifyRequest>("edit") {
                editUserInfo(call.userId!!, it)
            }

            @Serializable
            data class ChangePasswordRequest(
                val old: String,
                val new: String,
            )
            post<ChangePasswordRequest>("/change-password") {
                val user = call.user!!
                if (!user.checkPassword(it.old)) {
                    call.respond(failRestResponseDefault(-1, "incorrect password"))
                    return@post
                }

                transaction {
                    user.changePassword(it.new)
                }
                call.respond(emptyRestResponse("password changed"))
            }

            route("/{uid}") {
                get {
                    newSuspendedTransaction {
                        val user = call.parameters["uid"]?.toLongOrNull()?.let {
                            User.findById(it)
                        } ?: run {
                            call.respond(failRestResponseDefault(-1, "user not found"))
                            return@newSuspendedTransaction
                        }

                        call.respond(user.asPublicResponse())
                    }
                }

                authenticate("admin") {
                    post<UserModifyRequest>("/edit") {
                        val id = call.parameters["uid"]?.toLongOrNull() ?: run {
                            call.respond(failRestResponseDefault(-1, "user not found"))
                            return@post
                        }

                        editUserInfo(id, it)
                    }
                }
            }

            get("/favorite") {
                newSuspendedTransaction {
                    call.respond(
                        call.user!!
                            .favoriteEvents
                            .map { it.asDetail() }
                            .asRestResponse()
                    )
                }
            }

            get("/history") {
                newSuspendedTransaction {
                    call.respond(
                        call.user!!
                            .historyEvents
                            .map { it.asDetail() }
                            .asRestResponse()
                    )
                }
            }
        }
    }
}

val ApplicationCall.userId get() = principal<JWTPrincipal>()?.payload?.getClaim("id")?.asLong()

val ApplicationCall.user
    get() = transaction {
        userId?.let { User.findById(it) }
    }