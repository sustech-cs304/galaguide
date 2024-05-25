package galaGuide.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.asDetail
import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.data.user.asPrivateResponse
import galaGuide.data.user.asPublicResponse
import galaGuide.table.staticAsset
import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
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
            val userRole: Int = 1,
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
                ).asRestResponse()
            )
        }

        authenticate("user") {
            val requestInterval = environment?.config?.property("user.email-verify.request-interval")?.getString()
                ?.toLongOrNull()?.seconds ?: 1.minutes
            val codeExpireTime =
                environment?.config?.property("user.email-verify.expire-time")?.getString()?.toLongOrNull()?.seconds
                    ?: 1.hours
            val smtpServerHost = environment?.config?.property("user.email-verify.host")?.getString()
                ?: throw IllegalArgumentException("user.email-verify.host not found")
            val smtpServerPort = environment?.config?.property("user.email-verify.port")?.getString()?.toIntOrNull()
            val smtpServerUsername = environment?.config?.property("user.email-verify.username")?.getString()
            val smtpServerPassword = environment?.config?.property("user.email-verify.password")?.getString()
            val sender = environment?.config?.property("user.email-verify.sender")?.getString()
                ?: "None"
            val senderEmail = environment?.config?.property("user.email-verify.sender-email")?.getString()
                ?: throw IllegalArgumentException("user.email-verify.sender-email not found")
            val title = environment?.config?.property("user.email-verify.title")?.getString()
                ?: "Galaguide Email Verification"
            val template = environment?.config?.property("user.email-verify.template")?.getString()
                ?: "Welcome to Galaguide! Your email verification code is: %s"

            val smtpServer by lazy {
                MailerBuilder.withSMTPServerHost(smtpServerHost).run {
                    smtpServerPort?.let {
                        withSMTPServerPort(it)
                    }
                    smtpServerUsername?.let {
                        withSMTPServerUsername(it)
                    }
                    smtpServerPassword?.let {
                        withSMTPServerPassword(it)
                    }
                    buildMailer()
                }
            }

            data class EmailVerifyInfo(
                val code: String,
                val nextRequestTime: kotlinx.datetime.Instant = Clock.System.now() + requestInterval,
                val expireTime: kotlinx.datetime.Instant = Clock.System.now() + codeExpireTime,
            )

            val emailVerifyMap = mutableMapOf<Long, EmailVerifyInfo>()
            val emailLogger = KtorSimpleLogger(smtpServer::class.qualifiedName!!)

            post("/request-email") {
                val user = call.user!!
                if (user.emailVerified) {
                    call.respond(failRestResponseDefault(-1, "email already verified"))
                    return@post
                }

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
                    val email = EmailBuilder.startingBlank()
                        .from(sender, senderEmail)
                        .to(user.name, user.email)
                        .withSubject(title)
                        .withPlainText(template.format(code))
                        .buildEmail()

                    smtpServer.sendMail(email)
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
                if (user.emailVerified) {
                    call.respond(failRestResponseDefault(-1, "email already verified"))
                    return@post
                }

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

            get {
                newSuspendedTransaction {
                    call.respond(call.user!!.asPrivateResponse())
                }
            }

            @Serializable
            data class UserModifyRequest(
                val name: String? = null,
                val email: String? = null,
                val avatarId: String? = null,
                val backgroundId: String? = null,
                val intro: String? = null,
            )
            post<UserModifyRequest>("edit") {
                newSuspendedTransaction {
                    it.name?.let {
                        if (!User.checkNameAvailable(it)) {
                            call.respond(failRestResponseDefault(-1, "name already exists"))
                            return@newSuspendedTransaction
                        }
                    }

                    it.email?.let {
                        if (!User.checkEmailAvailable(it)) {
                            call.respond(failRestResponseDefault(-2, "invalid email"))
                            return@newSuspendedTransaction
                        }
                    }

                    val avatar = it.avatarId?.let {
                        it.staticAsset ?: run {
                            call.respond(failRestResponseDefault(-3, "avatar not found"))
                            return@newSuspendedTransaction
                        }
                    }

                    val background = it.backgroundId?.let {
                        it.staticAsset ?: run {
                            call.respond(failRestResponseDefault(-4, "background not found"))
                            return@newSuspendedTransaction
                        }
                    }

                    val user = call.user!!
                    it.name?.let { user.name = it }
                    it.email?.let {
                        user.email = it
                        user.emailVerified = false
                    }
                    avatar?.let { user.avatar = it }
                    background?.let { user.background = it }
                    it.intro?.let { user.intro = it }
                }
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

            get("/{uid}") {
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