package galaGuide.table.reservation

import galaGuide.table.Event
import galaGuide.table.EventPeriod
import galaGuide.table.EventPeriodTable
import galaGuide.table.EventTable
import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import galaGuide.util.createThis
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp

object ReservationTable : LongIdTable() {
    val initiatorId = reference("initiator_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val recipientId = reference("recipient_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val status = enumerationByName("status", 20, OrderStatus::class)
    val price = long("cost").default(0)
    val createTime = timestamp("create_time")
    val eventId = reference("event_id", EventTable, onDelete = ReferenceOption.CASCADE)
    val periodId = reference("period_id", EventPeriodTable, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 128)
    val phoneNumber = varchar("phone_number", 16)
    val email = varchar("email", 128)
    init {
        createThis()
    }
}

class Order(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Order>(ReservationTable)

    var initiator by User referencedOn ReservationTable.initiatorId
    var recipient by User referencedOn ReservationTable.recipientId
    var status by ReservationTable.status
    var price by ReservationTable.price
    var createTime by ReservationTable.createTime
    var event by Event referencedOn ReservationTable.eventId
    var period by EventPeriod referencedOn ReservationTable.periodId
    var name by ReservationTable.name
    var phoneNumber by ReservationTable.phoneNumber
    var email by ReservationTable.email
}

enum class OrderStatus {
    NONPAYMENT,
    CONFIRMED,
    USED,
    REFUNDING,
    CLOSED,
    ERROR
}