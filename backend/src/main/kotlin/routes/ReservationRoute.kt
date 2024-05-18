package galaGuide.routes

import galaGuide.table.reservation.Order
import galaGuide.table.reservation.OrderStatus
import galaGuide.table.reservation.ReservationTable
import galaGuide.table.user.User
import galaGuide.table.Event
import galaGuide.table.EventPeriod
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDateTime

import kotlinx.serialization.Contextual
import org.jetbrains.exposed.sql.SchemaUtils


fun Route.routeReservation() = authenticate("user") {
    route("/reserve") {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(ReservationTable)
        }

        // 创建订单路由
        createOrderRoute()

        // 更改订单状态路由
        orderNextRoute()
    }
}

fun Route.createOrderRoute() {
    post("/create-order") {
        val orderRequest = kotlin.runCatching { call.receiveNullable<OrderRequest>() }.getOrNull()
        if (orderRequest == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
            return@post
        }

        val initiatorId = orderRequest.initiatorId
        val recipientId = orderRequest.recipientId
        val eventId = orderRequest.eventId
        val periodId = orderRequest.periodId
        val price = orderRequest.price
        val createTime = LocalDateTime.now()
        val status = OrderStatus.NONPAYMENT

        // 获取当前用户信息
        val currentUser = call.authentication.principal<User>()
        val currentUserId: Long? = currentUser?.id?.value
        // 检查当前用户是否有权限创建订单
        if (currentUserId != initiatorId) {
            call.respond(HttpStatusCode.Forbidden, "You do not have permission to create this order")
            return@post
        }

        val initiator = transaction { User.findById(initiatorId) }
        if (initiator == null) {
            call.respond(HttpStatusCode.BadRequest, "Initiator not found")
            return@post
        }
        val recipient = transaction { User.findById(recipientId) }


        if (recipient == null) {
            call.respond(HttpStatusCode.BadRequest, "Recipient not found")
            return@post
        }

        val event = transaction { Event.findById(eventId) }
        if (event == null) {
            call.respond(HttpStatusCode.BadRequest, "Event not found")
            return@post
        }

        val period = transaction { EventPeriod.findById(periodId) }
        if (period == null) {
            call.respond(HttpStatusCode.BadRequest, "Period not found")
            return@post
        }
        if (period.event != event) {
            call.respond(HttpStatusCode.BadRequest, "Event not equal with period")
            return@post
        }

        val orderId = ReservationTable.insertAndGetId { order ->
            order[ReservationTable.initiatorId] = initiatorId
            order[ReservationTable.recipientId] = recipientId
            order[ReservationTable.eventId] = eventId
            order[ReservationTable.periodId] = periodId
            order[ReservationTable.price] = price
            order[ReservationTable.createTime] = createTime
            order[ReservationTable.status] = status
        }
        kotlin.runCatching { call.respond(HttpStatusCode.Created, "Order created with ID: $orderId") }
            .getOrElse {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to create order: ${it.message}"
                )
            }
    }
}

fun Route.orderNextRoute() {
    post("/order-next") {
        val (orderId, action) = call.receive<OrderNextRequest>()

        // 获取当前用户信息
        val currentUser = call.authentication.principal<User>()

        val result = kotlin.runCatching {
            val order = Order.findById(orderId) ?: run {
                call.respond(HttpStatusCode.BadRequest, "Order not found")
                return@post
            }

            // 检查当前用户是否有权限更改订单状态
            if (currentUser?.id != order.initiatorId) {
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

@Serializable
data class OrderRequest(
    val initiatorId: Long,
    val recipientId: Long,
    val eventId: Long,
    val periodId: Long,
    @Contextual val price: BigDecimal
)

data class OrderNextRequest(val orderId: Long, val action: Int)

