package galaGuide.routes

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.routeContact() {
    route("/contact") {
        routeGroup()
    }
}

private fun Route.routeGroup() {
    route("/group") {
        authenticate("user") {
            post("/create") {

            }
        }
    }
}