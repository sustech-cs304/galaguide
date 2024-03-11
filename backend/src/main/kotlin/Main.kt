package galaGuide

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    DatabaseConfig.init()
    EngineMain.main(args)
}

fun Application.init() {
    install(ContentNegotiation) {
        @OptIn(ExperimentalSerializationApi::class)
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            allowStructuredMapKeys = true
            allowTrailingComma = true
        })
    }
    install(Resources)
    install(CallLogging)

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
            challenge { _, _ ->
                call.respondRedirect("/login")
            }
        }
    }

    configureRouting()
}