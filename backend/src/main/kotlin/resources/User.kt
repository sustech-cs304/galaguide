package galaGuide.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.failRestResponse
import galaGuide.table.UserTable
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest
import java.time.Instant
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

@Resource("/user")
class User {
    @Resource("register")
    class Register(val parent: User = User(), val name: String, val password: String, val email: String)

    @Resource("login")
    class Login(val parent: User = User(), val nameOrEmail: String, val password: String)
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

    get<User> {
        call.respondRedirect("/user/login")
    }

    post<User.Register> {
        transaction {
            UserTable.select {
                UserTable.name eq it.name
            }.firstOrNull()
        }?.let {
            call.respond(failRestResponse<Any>(-1, "user already exists"))
            return@post
        }

        if (it.name.length > 32) {
            call.respond(failRestResponse<Any>(-1, "name too long"))
            return@post
        }

        if (it.email.length > 128) {
            call.respond(failRestResponse<Any>(-1, "email too long"))
            return@post
        }
        val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")
        if (!emailRegex.matches(it.email)) {
            call.respond(failRestResponse<Any>(-1, "invalid email"))
            return@post
        }

        if (it.password.length !in 6..128) {
            call.respond(failRestResponse<Any>(-1, "password length should be 6-128"))
            return@post
        }

        val passwordEncrypted = MessageDigest.getInstance("SHA-256")
            .digest(it.password.toByteArray())
            .joinToString("") { byte ->
                "%02x".format(byte)
            }

        val id = transaction {
            UserTable.insertAndGetId { statement ->
                statement[name] = it.name
                statement[password] = passwordEncrypted
                statement[email] = it.email
            }
        }.value

        call.respond(mapOf("token" to generateToken(id)))
    }

    post<User.Login> {
        val passwordEncrypted = MessageDigest.getInstance("SHA-256")
            .digest(it.password.toByteArray())
            .joinToString("") { byte ->
                "%02x".format(byte)
            }

        val user = transaction {
            UserTable.select {
                (UserTable.name eq it.nameOrEmail) or (UserTable.email eq it.nameOrEmail)
            }.firstOrNull()
        } ?: run {
            call.respond(failRestResponse<Any>(-1, "user not found"))
            return@post
        }

        if (user[UserTable.password] != passwordEncrypted) {
            call.respond(failRestResponse<Any>(-1, "password incorrect"))
            return@post
        }

        call.respond(mapOf("token" to generateToken(user[UserTable.id].value)))
    }
}