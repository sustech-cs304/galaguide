package galaGuide.routes

import galaGuide.data.*
import galaGuide.resources.user
import galaGuide.resources.userId
import galaGuide.table.*
import galaGuide.table.reservation.Order
import galaGuide.table.user.UserFavoriteEventTable
import galaGuide.table.user.UserHistoryEventTable
import galaGuide.util.GroupManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

fun Route.routeEvent() {
    route("/event") {
        get {
            call.respond(transaction {
                Event.all().map { it.asDetail() }
            }.asRestResponse())
        }
        post<EventFilter>("/filter") {
            if (it.equals(null)) {
                val reply = transaction { Event.all().toList() }
                call.respond(transaction { reply.asRestResponse() })
                return@post
            }
            val reply = transaction {
                val categoryItems: Set<Event> = if (it.category.isNullOrEmpty()) {
                    Event.all().toSet()
                } else {
                    Event.all().filter { event -> event.category in it.category }.toSet()
                }
                val searchItems: Set<Event> = if (it.searchQuery == "") {
                    Event.all().toSet()
                } else {
                    Event.all()
                        .filter { event -> (it.searchQuery in event.title) or (it.searchQuery in event.description) }
                        .toSet()
                }
                val periods = EventPeriod.find {
                    (EventPeriodTable.end less Instant.ofEpochSecond(it.startDate)) or (EventPeriodTable.start greater Instant.ofEpochSecond(
                        it.endDate
                    ))
                }
                val dateItems =
                    Event.all().filter { dataItem -> dataItem.id in periods.map { p -> p.event.id } }.toSet()

                val priceItems =
                    Event.find { (EventTable.cost greaterEq it.minPrice) and (EventTable.cost lessEq it.maxPrice) }
                        .toSet()

                categoryItems.intersect(dateItems).intersect(priceItems).intersect(searchItems).toList()
            }
            call.respond(transaction { reply.asRestResponse() })
        }

        get("/top-rated") {
            if (transaction { Order.all().empty() }) {
                newSuspendedTransaction {
                    call.respond(Event.all().take(10).asRestResponse())
                }
                return@get
            }
            newSuspendedTransaction {
                val link1 = Order.all().groupBy { it.event }
//                    logger.info("link1:{}", link1)
                val link2 = link1.map { (event, orders) -> EventCount(event, orders.count()) }
//                    logger.info("link2:{}", link2)
                val link3 = link2.sortedByDescending { it.count }
//                    logger.info("link3:{}", link3)
                val link4 = link3.map { (event, _) -> event }
//                    logger.info("link4:{}", link4)
                val link5 = link4.take(10)

                logger.info("reply:$link5")
                call.respond(link5.asRestResponse())
            }
        }
        get("/newest") {
            newSuspendedTransaction {
                val reply =
                    Event.all().sortedByDescending { it.id }.take(10).toList()
                call.respond(reply.asRestResponse())
            }
        }
        route("/{id}") {
            authenticate("user") {
                get {
                    val id = call.parameters["id"]?.toLongOrNull() ?: run {
                        call.respond(failRestResponseDefault(-1, "Invalid ID"))
                        return@get
                    }

                    val event = transaction {
                        Event.findById(id)?.also { e ->
                            call.userId?.let { userId ->
                                UserHistoryEventTable.insert {
                                    it[user] = userId
                                    it[event] = e.id
                                }
                            }
                        }?.asRestResponse()
                    } ?: run {
                        call.respond(failRestResponseDefault(-2, "Event not found"))
                        return@get
                    }
                    logger.info("Out User Logged in")
                    if (call.user != null) {
                        logger.info("In User Logged in")
                        UserHistoryEventTable.insert {
                            it[user] = call.user!!.id
                            it[UserHistoryEventTable.event] = id
                        }
                    }
                    call.respond(event)
                }
            }

            authenticate("admin") {
                post("review") {
                    val id = call.parameters["id"]?.toLongOrNull() ?: run {
                        call.respond(failRestResponseDefault(-1, "Invalid ID"))
                        return@post
                    }

                    val event = transaction {
                        Event.findById(id)
                    } ?: run {
                        call.respond(failRestResponseDefault(-2, "Event not found"))
                        return@post
                    }

                    transaction {
                        event.reviewed = true
                    }
                    call.respond(emptyRestResponse("Event reviewed"))
                }
            }

            authenticate("user") {
                post("/favorite") {
                    val userId = call.userId!!
                    val eventId = call.parameters["id"]?.toLongOrNull() ?: run {
                        call.respond(failRestResponseDefault(-1, "Invalid ID"))
                        return@post
                    }

                    newSuspendedTransaction {
                        Event.findById(eventId) ?: run {
                            call.respond(failRestResponseDefault(-2, "Event not found"))
                            return@newSuspendedTransaction
                        }

                        if (UserFavoriteEventTable.select { (UserFavoriteEventTable.user eq userId) and (UserFavoriteEventTable.event eq eventId) }
                                .any()) {
                            UserFavoriteEventTable.deleteWhere { (user eq userId) and (event eq eventId) }
                        } else {
                            UserFavoriteEventTable.insert {
                                it[user] = userId
                                it[event] = eventId
                            }
                        }

                        call.respond(emptyRestResponse("Favorite toggled"))
                    }
                }
            }
        }

        authenticate("admin") {
            get("/unreviewed") {
                call.respond(transaction {
                    Event.find { EventTable.reviewed eq false }.map { it.asDetail() }
                }.asRestResponse())
            }
        }

        authenticate("user") {
            post<EventDetail>("/create") { it ->
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

                if (it.periods.any { it.start == null || it.end == null }) {
                    call.respond(failRestResponseDefault(-4, "Period start or end not provided"))
                    return@post
                }

                newSuspendedTransaction {
                    val poster = StaticAsset.findById(UUID.fromString(it.posterId)) ?: run {
                        call.respond(failRestResponseDefault(-5, "Poster not found"))
                        return@newSuspendedTransaction
                    }

                    it.assetIds?.forEach { uuid ->
                        StaticAsset.findById(UUID.fromString(uuid)) ?: run {
                            call.respond(failRestResponseDefault(-6, "Asset $uuid not found"))
                            return@newSuspendedTransaction
                        }
                    }

                    val event = Event.new {
                        title = it.title
                        host = call.user!!
                        this.poster = poster
                        description = it.description!!
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
                            start = Instant.ofEpochSecond(it.start!!)
                            end = Instant.ofEpochSecond(it.end!!)
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
