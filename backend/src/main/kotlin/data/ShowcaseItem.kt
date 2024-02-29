package galaGuide.data

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ShowcaseItem(val title: String, val link: String, val preview: String? = null)

fun Route.routeShowcase() = get("/showcase") {
    val example = listOf(
        ShowcaseItem("We're building this software!", "http://localhost:8080/showcase"),
    )

    call.respond(example)
}