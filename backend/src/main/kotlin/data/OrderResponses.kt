package galaGuide.data

import galaGuide.table.reservation.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetail(
    var initiatorId: Long? = null,
    var recipientId: Long? = null,
    var status: String? = null,
    var price: Long? = null,
    var createTime: Long? = null,
    var eventId: Long? = null,
    var periodId: Long? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
)

fun Order.asDetail() = OrderDetail(
    initiatorId = initiator.id.value,
    recipientId = recipient.id.value,
    status = status.name,
    price = price,
    createTime = createTime.epochSecond,
    eventId = event.id.value,
    periodId = period.id.value,
    name = name,
    phoneNumber = phoneNumber,
    email = email,
)

@Serializable
data class OrderNext(val orderId: Long, val action: Int)

@Serializable
data class OrderData(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val eventId: Long,
    val periodId: Long,
)

fun Order.asRestResponse() = asDetail().asRestResponse()
fun List<Order>.asRestResponse() = map { it.asDetail() }.asRestResponse()