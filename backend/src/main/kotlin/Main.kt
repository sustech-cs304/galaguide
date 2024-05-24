package galaGuide

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.failRestResponseDefault
import galaGuide.table.user.User
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
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
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

    val secret = environment.config.property("user-jwt.secret").getString()
    val issuer = environment.config.property("user-jwt.issuer").getString()
    val audience = environment.config.property("user-jwt.audience").getString()
    val userRealm = environment.config.property("user-jwt.realm").getString()
    install(Authentication) {
        jwt("user") {
            realm = userRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate {
                transaction {
                    if (User.findById(
                            it.payload.getClaim("id").asLong() ?: -1
                        ) != null
                    ) JWTPrincipal(it.payload) else null
                }
            }
            challenge { _, _ ->
                call.respond(failRestResponseDefault(-403, "Forbidden"))
            }
        }
    }

    configureRouting()
}