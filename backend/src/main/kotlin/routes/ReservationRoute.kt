package galaGuide.routes

import galaGuide.data.*
import galaGuide.resources.user
import galaGuide.table.Event
import galaGuide.table.EventPeriod
import galaGuide.table.reservation.Order
import galaGuide.table.reservation.OrderStatus
import galaGuide.table.reservation.ReservationTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*


fun Route.routeReservation() = authenticate("user") {
    route("/reserve") {
        // 创建订单路由
        createOrderRoute()
        // 更改订单状态路由
        orderNextRoute()
        myReserveRoute()
    }
}

fun Route.createOrderRoute() {
    post<OrderData>("/create-order") {
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
        if (transaction { period.event.id  != event.id}) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: PeriodId(Mismatch with EventId)"))
            return@post
        }
        val reply = transaction {
            Order.new {
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
        }
        kotlin.runCatching { call.respond(emptyRestResponse("Operation Success: Create Order")) }
        return@post
    }
}


fun Route.myReserveRoute() {
    get("/mine") {
        if (call.user == null) {
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
        val (orderId, action) = call.receive<OrderNext>()

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

