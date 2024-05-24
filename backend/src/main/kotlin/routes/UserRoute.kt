package galaGuide.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.asDetail
import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.data.user.asPrivateResponse
import galaGuide.data.user.asPublicResponse
import galaGuide.table.Event
import galaGuide.table.user.User
import galaGuide.table.user.UserFavoriteEventTable
import galaGuide.table.user.UserTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import java.security.MessageDigest
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

    transaction {
        SchemaUtils.createMissingTablesAndColumns(UserTable)
    }

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
            transaction {
                User.find {
                    UserTable.name eq it.name
                }.firstOrNull()
            }?.let {
                call.respond(failRestResponseDefault(-1, "user already exists"))
                return@post
            }

            if (it.name.length > 32) {
                call.respond(failRestResponseDefault(-1, "name too long"))
                return@post
            }

            if (it.email.length > 128) {
                call.respond(failRestResponseDefault(-1, "email too long"))
                return@post
            }
            val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")
            if (!emailRegex.matches(it.email)) {
                call.respond(failRestResponseDefault(-1, "invalid email"))
                return@post
            }

            if (it.password.length !in 6..128) {
                call.respond(failRestResponseDefault(-1, "password length should be 6-128"))
                return@post
            }

            val passwordEncrypted = MessageDigest.getInstance("SHA-256")
                .digest(it.password.toByteArray())
                .joinToString("") { byte ->
                    "%02x".format(byte)
                }

            val user = transaction {
                User.new {
                    name = it.name
                    password = passwordEncrypted
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
            val passwordEncrypted = MessageDigest.getInstance("SHA-256")
                .digest(it.password.toByteArray())
                .joinToString("") { byte ->
                    "%02x".format(byte)
                }

            val user = transaction {
                User.find {
                    (UserTable.name eq it.nameOrEmail) or (UserTable.email eq it.nameOrEmail)
                }.firstOrNull()
            } ?: run {
                call.respond(failRestResponseDefault(-1, "user not found"))
                return@post
            }

            if (user.password != passwordEncrypted) {
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
                call.respond(call.user!!.asPrivateResponse())
            }

            get("/{uid}") {
                val user = call.parameters["uid"]?.toLongOrNull()?.let {
                    transaction {
                        User.findById(it)
                    }
                } ?: run {
                    call.respond(failRestResponseDefault(-1, "user not found"))
                    return@get
                }

                call.respond(user.asPublicResponse())
            }

            get("/favorite") {
                val userId = call.userId!!
                val list = transaction {
                    UserFavoriteEventTable.select { UserFavoriteEventTable.user eq userId }
                        .mapNotNull { Event.findById(it[UserFavoriteEventTable.event])?.asDetail() }
                }
                call.respond(list.asRestResponse())
            }
        }
    }
}

val ApplicationCall.userId get() = principal<JWTPrincipal>()?.payload?.getClaim("id")?.asLong()

val ApplicationCall.user
    get() = transaction {
        userId?.let { User.findById(it) }
    }