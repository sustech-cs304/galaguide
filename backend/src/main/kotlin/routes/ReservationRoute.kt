package galaGuide.routes

import galaGuide.data.asDetail
import galaGuide.data.asRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.table.Event
import galaGuide.table.EventPeriod
import galaGuide.table.EventPeriodTable
import galaGuide.table.EventTable
import galaGuide.table.reservation.Order
import galaGuide.table.reservation.OrderStatus
import galaGuide.table.reservation.ReservationTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*


fun Route.routeReservation() = authenticate("user") {
    route("/reserve") {
        // 创建订单路由
        createOrderRoute()
        filterOrderRoute()
        // 更改订单状态路由
        orderNextRoute()
        topRatedRoute()
        myReserveRoute()
    }
}

fun Route.createOrderRoute() {
    post<ReservationRoute.Order.Object>("/create-order") {
        if (it.equals(null)) {
            call.respond(failRestResponseDefault(-1, "Missing request body"))
            return@post
        }

        // 获取当前用户信息
        val currentUser = call.user
        if (currentUser == null) {
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@post
        }

        val event = transaction { Event.findById(it.eventId) }
        if (event == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: EventId"))
            return@post
        }

        val period = transaction { EventPeriod.findById(it.periodId) }
        if (period == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: PeriodId"))
            return@post
        }
        if (period.event != event) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: PeriodId(Mismatch with EventId)"))
            return@post
        }

        val reply = Order.new {
            initiator = currentUser
            recipient = event.host
            status = OrderStatus.NONPAYMENT
            price = event.cost
            createTime = Instant.ofEpochSecond(Date().time)
            this.event = event
            this.period = period
            name = it.name
            phoneNumber = it.phoneNumber
            email = it.email
        }

        kotlin.runCatching { call.respond(reply.asDetail().asRestResponse()) }
    }
}

fun Route.filterOrderRoute() {
    post<ReservationRoute.Filter.Object>("/filter-order") {
        if (it.equals(null)) {
            val reply = transaction { Event.all().toList() }
            call.respond(reply.asRestResponse())
            return@post
        }
        val reply = transaction {
            var categoryItems = Event.all().toSet()
            var statusItems = Event.all().toSet()
            val dateItems = Event.all().toSet()
            var priceItems = Event.all().toSet()
            if (!it.status.equals(null)) {
                categoryItems = Event.find { EventTable.category eq it.category }.toSet()
            }
            if (!it.status.equals(null)) {
                statusItems = Event.find { EventTable.category eq it.category }.toSet()
            }
            if (!it.startDate.equals(null) and !it.endDate.equals(null)) {
                val periods = EventPeriod.find {
                    (EventPeriodTable.end less Instant.ofEpochSecond(it.startDate)) or (EventPeriodTable.start greater Instant.ofEpochSecond(
                        it.endDate
                    ))
                }
                dateItems.filter { dataItem -> dataItem.id in periods.map { p -> p.event.id } }
            }
            if (!it.minPrice.equals(null) and !it.endDate.equals(null)) {
                priceItems =
                    Event.find { (EventTable.cost greaterEq it.minPrice) and (EventTable.cost lessEq it.maxPrice) }
                        .toSet()
            }
            categoryItems.intersect(statusItems).intersect(dateItems).intersect(priceItems).toList()
        }
        call.respond(reply.asRestResponse())
        return@post
    }
}

fun Route.topRatedRoute() {
    get("/event/top-rated") {
        val reply = transaction {
            Order.all().groupBy { it.event }
                .map { (event, orders) -> ReservationRoute.EventCount.Object(event, orders.count()) }.sortedByDescending { it.count }.take(10).map { (event, _) -> event }
        }
        call.respond(reply.asRestResponse())
        return@get
    }
}
fun Route.myReserveRoute(){
    get("/reserve/mine"){
        if(call.user == null){
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@get
        }
        val reply = transaction { Order.find { ReservationTable.initiatorId eq call.user!!.id }.toList() }
        call.respond(reply.asRestResponse())
        return@get
    }
}
fun Route.orderNextRoute() {
    post("/order-next") {
        val (orderId, action) = call.receive<ReservationRoute.OrderNext.Object>()

        // 获取当前用户信息
        val currentUser = call.user

        val result = kotlin.runCatching {
            val order = Order.findById(orderId) ?: run {
                call.respond(HttpStatusCode.BadRequest, "Order not found")
                return@post
            }

            // 检查当前用户是否有权限更改订单状态
            if (currentUser?.id != order.initiator.id) {
                call.respond(HttpStatusCode.Forbidden, "You do not have permission to update the status of this order")
                return@post
            }
            transaction {
                val updatedStatus = when (order.status) {
                    OrderStatus.NONPAYMENT -> {
                        when (action) {
                            1 -> OrderStatus.CONFIRMED
                            -1 -> OrderStatus.CLOSED
                            else -> OrderStatus.ERROR
                        }
                    }

                    OrderStatus.CONFIRMED -> {
                        when (action) {
                            1 -> OrderStatus.USED
                            -1 -> OrderStatus.REFUNDING
                            else -> OrderStatus.ERROR
                        }
                    }

                    OrderStatus.USED -> {
                        when (action) {
                            1 -> OrderStatus.CLOSED
                            -1 -> OrderStatus.CONFIRMED
                            else -> OrderStatus.ERROR
                        }
                    }

                    OrderStatus.REFUNDING -> {
                        when (action) {
                            1 -> OrderStatus.CLOSED
                            -1 -> OrderStatus.CONFIRMED
                            else -> OrderStatus.ERROR
                        }
                    }

                    else -> OrderStatus.ERROR
                }

                order.status = updatedStatus
            }

            call.respond(HttpStatusCode.OK, "Order status updated successfully: ${order.status}")
        }

        result.onFailure {
            call.respond(HttpStatusCode.InternalServerError, "Failed to update order status: ${it.message}")
        }
    }
}

class ReservationRoute {
    class Order {
        @Serializable
        data class Object(
            val name: String,
            val phoneNumber: String,
            val email: String,
            val eventId: Long,
            val periodId: Long,
        )
    }

    class OrderNext {
        @Serializable
        data class Object(val orderId: Long, val action: Int)
    }

    class Filter {
        @Serializable
        data class Object(
            val category: String,
            val startDate: Long,
            val endDate: Long,
            val status: String,
            val maxPrice: Long,
            val minPrice: Long,
        )
    }

    class EventCount {
        data class Object(
            val event: Event,
            val count: Int
        )
    }
}

