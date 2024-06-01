package galaGuide

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.failRestResponseDefault
import galaGuide.table.user.User
import galaGuide.table.user.UserRole
import galaGuide.util.SMTP
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.util.logging.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    DatabaseConfig.init()
    EngineMain.main(args)
}

fun Application.init() {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        isLenient = true
        allowStructuredMapKeys = true
        allowTrailingComma = true
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(Resources)
    install(CallLogging)
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(json)
    }
    install(PartialContent)
    install(AutoHeadResponse)

    install(StatusPages) {
        val logger = KtorSimpleLogger(Application::class.qualifiedName!!)
        exception<Exception> { call, e ->
            logger.error(e)
            call.respond(failRestResponseDefault(-500, e.localizedMessage ?: "Internal Server Error"))
        }
    }

    val secret = environment.config.property("user-jwt.secret").getString()
    val issuer = environment.config.property("user-jwt.issuer").getString()
    val audience = environment.config.property("user-jwt.audience").getString()
    val unverifiedRealm = environment.config.property("user-jwt.unverified-realm").getString()
    val userRealm = environment.config.property("user-jwt.user-realm").getString()
    val adminRealm = environment.config.property("user-jwt.admin-realm").getString()
    install(Authentication) {
        val verifier = JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

        jwt("unverified") {
            realm = unverifiedRealm
            verifier(verifier)
            validate {
                transaction {
                    if (User.findById(
                            it.payload.getClaim("id").asLong() ?: -1
                        )?.emailVerified == false
                    ) JWTPrincipal(it.payload) else null
                }
            }
            challenge { _, _ ->
                call.respond(failRestResponseDefault(-403, "Forbidden"))
            }
        }

        jwt("user") {
            realm = userRealm
            verifier(verifier)
            validate {
                transaction {
                    val user = User.findById(
                        it.payload.getClaim("id").asLong() ?: -1
                    )
                    if (user != null && user.emailVerified) JWTPrincipal(it.payload) else null
                }
            }
            challenge { _, _ ->
                call.respond(failRestResponseDefault(-403, "Forbidden"))
            }
        }

        jwt("admin") {
            realm = adminRealm
            verifier(verifier)
            validate {
                transaction {
                    if (User.findById(
                            it.payload.getClaim("id").asLong() ?: -1
                        )?.role == UserRole.ADMIN
                    ) JWTPrincipal(it.payload) else null
                }
            }
            challenge { _, _ ->
                call.respond(failRestResponseDefault(-403, "Forbidden"))
            }
        }
    }

    SMTP.init(this)

    configureRouting()
}