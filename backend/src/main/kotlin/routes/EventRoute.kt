package galaGuide.routes

import galaGuide.data.EventDetail
import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.resources.userId
import galaGuide.table.Event
import galaGuide.table.EventAssetTable
import galaGuide.table.EventPeriod
import galaGuide.table.StaticAsset
import galaGuide.table.user.UserFavoriteEventTable
import galaGuide.util.GroupManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

fun Route.routeEvent() {
    route("/event") {
        get {
            call.respond(transaction {
                Event.all().map { it.asRestResponse() }
            })
        }

        route("/{id}") {
            get {
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
                post("/favorite") {
                    val userId = call.userId!!
                    val eventId = call.parameters["id"]?.toLongOrNull() ?: run {
                        call.respond(failRestResponseDefault(-1, "Invalid ID"))
                        return@post
                    }

                    transaction {
                        if (UserFavoriteEventTable.select { (UserFavoriteEventTable.user eq userId) and (UserFavoriteEventTable.event eq eventId) }
                                .any()) {
                            UserFavoriteEventTable.deleteWhere { (user eq userId) and (event eq eventId) }
                        } else {
                            UserFavoriteEventTable.insert {
                                it[user] = userId
                                it[event] = eventId
                            }
                        }
                    }

                    call.respond(emptyRestResponse("Favorite toggled"))
                }
            }
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