package galaGuide.routes

import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.table.Event
import galaGuide.table.EventAssetTable
import galaGuide.table.EventPeriod
import galaGuide.table.StaticAsset
import galaGuide.util.GroupManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

fun Route.routeEvent() {
    @Serializable
    data class EventPeriodDetail(
        val id: Long,
        val start: Long,
        val end: Long,
    )

    fun EventPeriod.asDetail() = EventPeriodDetail(
        id.value,
        start.epochSecond,
        end.epochSecond,
    )

    @Serializable
    data class EventDetail(
        val id: Long? = null,
        val title: String? = null,
        val hostId: Long? = null,
        val posterId: String? = null,
        val description: String? = null,
        val cost: Long? = null,
        val assetIds: List<String>? = null,
        val periods: List<EventPeriodDetail>? = null,
        val category: String? = null,
    )

    fun Event.asDetail() = EventDetail(
        id.value,
        title,
        host.id.value,
        poster.id.value.toString(),
        description,
        cost,
        assets.map { it.id.value.toString() },
        periods.map { it.asDetail() },
        category,
    )

    fun Event.asRestResponse() = asDetail().asRestResponse()

    route("/event") {
        get {
            call.respond(transaction {
                Event.all().map { it.asRestResponse() }
            })
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: run {
                call.respond(failRestResponseDefault(-1, "Invalid ID"))
                return@get
            }

            val event = transaction {
                Event.findById(id)?.asRestResponse()
            } ?: run {
                call.respond(failRestResponseDefault(-2, "Event not found"))
                return@get
            }

            call.respond(event)
        }

        authenticate("user") {
            post<EventDetail>("/create") {
                if (it.title == null) {
                    call.respond(failRestResponseDefault(-1, "Title not provided"))
                    return@post
                }
                if (it.posterId == null) {
                    call.respond(failRestResponseDefault(-2, "Poster not provided"))
                    return@post
                }

                if (it.periods?.isNotEmpty() != true) {
                    call.respond(failRestResponseDefault(-3, "There must be at least one period"))
                    return@post
                }

                newSuspendedTransaction {
                    val poster = StaticAsset.findById(UUID.fromString(it.posterId)) ?: run {
                        call.respond(failRestResponseDefault(-3, "Poster not found"))
                        return@newSuspendedTransaction
                    }

                    it.assetIds?.all { uuid ->
                        StaticAsset.findById(UUID.fromString(uuid)) != null
                    }.takeUnless { b ->
                        b == false
                    } ?: run {
                        call.respond(failRestResponseDefault(-4, "Asset not found"))
                        return@newSuspendedTransaction
                    }

                    val event = Event.new {
                        title = it.title
                        host = call.user!!
                        this.poster = poster
                        description = it.description
                        cost = it.cost ?: 0
                        category = it.category ?: "other"
                    }

                    it.assetIds?.forEach { uuid ->
                        EventAssetTable.insert {
                            it[EventAssetTable.event] = event.id
                            it[asset] = UUID.fromString(uuid)
                        }
                    }

                    it.periods.forEach {
                        EventPeriod.new {
                            this.event = event
                            start = Instant.ofEpochSecond(it.start)
                            end = Instant.ofEpochSecond(it.end)
                        }
                    }

                    GroupManager.createGroup(
                        GroupManager.GroupCreateOption(
                            name = it.title,
                        )
                    )
                }

                call.respond(emptyRestResponse("Event created"))
            }
        }
    }
}