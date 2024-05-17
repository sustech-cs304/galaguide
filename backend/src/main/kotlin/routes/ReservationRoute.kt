package galaGuide.routes

import galaGuide.table.reservation.ReservationTable
import galaGuide.table.user.User
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
        @Serializable
        data class OrderRequest(
            val initiatorId: Long,
            val recipientId: Long,
            val activityId: Long,
            val sessionId: Long,
            @Contextual val price: BigDecimal
        )

        fun Route.createOrderRoute() {
            post("/create-order") {
                val orderRequest = kotlin.runCatching { call.receiveNullable<OrderRequest>() }.getOrNull()
                if (orderRequest == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                    return@post
                }

                val initiatorId = orderRequest.initiatorId
                val recipientId = orderRequest.recipientId
                val activityId = orderRequest.activityId
                val sessionId = orderRequest.sessionId
                val price = orderRequest.price
                val createTime = LocalDateTime.now()
                val status = "NONPAYMENT"

                kotlin.runCatching {
                    val initiator = transaction { User.findById(initiatorId) }
                    val recipient = transaction { User.findById(recipientId) }

                    if (initiator == null) {
                        call.respond(HttpStatusCode.BadRequest, "Initiator not found")
                        return@post
                    }

                    if (recipient == null) {
                        call.respond(HttpStatusCode.BadRequest, "Recipient not found")
                        return@post
                    }

                    val orderId = ReservationTable.insertAndGetId { order ->
                        order[ReservationTable.initiatorId] = initiatorId
                        order[ReservationTable.recipientId] = recipientId
                        order[ReservationTable.activityId] = activityId
                        order[ReservationTable.sessionId] = sessionId
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
        }
    }
}