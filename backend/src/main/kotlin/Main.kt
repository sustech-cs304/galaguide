package galaGuide

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

fun main() {
    DatabaseConfig.init()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            @OptIn(ExperimentalSerializationApi::class)
            json(Json {
                prettyPrint = true
                isLenient = true
                allowStructuredMapKeys = true
                allowTrailingComma = true
            })
        }
        install(Resources)
        install(CallLogging)

        configureRouting()
    }.start(true)
}