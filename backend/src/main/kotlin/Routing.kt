package galaGuide

import galaGuide.data.routeShowcase
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() = routing {
    routeShowcase()
}