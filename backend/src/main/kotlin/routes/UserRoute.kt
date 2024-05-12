package galaGuide.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest
import java.time.Instant
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

@Resource("/user")
class UserRoute {
    @Resource("register")
    class Register(val parent: UserRoute = UserRoute()) {
        @Serializable
        data class Object(val name: String = "", val password: String = "", val email: String = "")
    }

    @Resource("login")
    class Login(val parent: UserRoute = UserRoute()) {
        @Serializable
        data class Object(val nameOrEmail: String = "", val password: String = "")
    }
}

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

    get<UserRoute> {
        call.respondRedirect("/user/login")
    }

    post<UserRoute.Register> {
        val obj = call.receive<UserRoute.Register.Object>()
        transaction {
            User.find {
                UserTable.name eq obj.name
            }.firstOrNull()
        }?.let {
            call.respond(failRestResponseDefault(-1, "user already exists"))
            return@post
        }

        if (obj.name.length > 32) {
            call.respond(failRestResponseDefault(-1, "name too long"))
            return@post
        }

        if (obj.email.length > 128) {
            call.respond(failRestResponseDefault(-1, "email too long"))
            return@post
        }
        val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")
        if (!emailRegex.matches(obj.email)) {
            call.respond(failRestResponseDefault(-1, "invalid email"))
            return@post
        }

        if (obj.password.length !in 6..128) {
            call.respond(failRestResponseDefault(-1, "password length should be 6-128"))
            return@post
        }

        val passwordEncrypted = MessageDigest.getInstance("SHA-256")
            .digest(obj.password.toByteArray())
            .joinToString("") { byte ->
                "%02x".format(byte)
            }

        val id = transaction {
            User.new {
                name = obj.name
                password = passwordEncrypted
                email = obj.email
            }
        }.id.value

        call.respond(emptyRestResponse())
    }

    post<UserRoute.Login> {
        val obj = call.receive<UserRoute.Login.Object>()
        val passwordEncrypted = MessageDigest.getInstance("SHA-256")
            .digest(obj.password.toByteArray())
            .joinToString("") { byte ->
                "%02x".format(byte)
            }

        val user = transaction {
            User.find {
                (UserTable.name eq obj.nameOrEmail) or (UserTable.email eq obj.nameOrEmail)
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
            mapOf(
                "token" to generateToken(user.id.value),
                "userRole" to 1,
                "userName" to user.name,
            ).asRestResponse()
        )
    }
}

val ApplicationCall.userId get() = principal<JWTPrincipal>()?.payload?.getClaim("id")?.asLong()