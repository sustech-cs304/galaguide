package galaGuide

import galaGuide.data.routeShowcase
import galaGuide.resources.routeUser
import galaGuide.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() = routing {
    routeShowcase()
    routeUser()
    routeWebSocket()
    routeAssetManage()
    routeReservation()
    routeEvent()
    routeContact()
    routeForum()
}