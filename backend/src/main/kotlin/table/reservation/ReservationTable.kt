package galaGuide.table.reservation

import galaGuide.table.user.UserTable // 引入 UserTable 类

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object ReservationTable : LongIdTable() {
    val initiatorId = reference("initiator_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val recipientId = reference("recipient_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val status = varchar("status", 20) // 添加订单状态字段
    val price = decimal("price", 10, 2)
    val createTime = datetime("create_time")
    val activityId = long("activity_id")
    val sessionId = long("session_id")
}

class Order(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Order>(ReservationTable)
    var initiatorId by ReservationTable.initiatorId
    var recipientId by ReservationTable.recipientId
    var status by ReservationTable.status
    var price by ReservationTable.price
    var createTime by ReservationTable.createTime
    var activityId by ReservationTable.activityId
    var sessionId by ReservationTable.sessionId
}
