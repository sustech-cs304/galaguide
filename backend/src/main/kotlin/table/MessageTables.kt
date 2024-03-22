package galaGuide.table

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object PrivateMessageTable : LongIdTable() {
    val from = reference("from", UserTable)
    val to = reference("to", UserTable)
    val content = text("content")
    val time = datetime("time")
}

class PrivateMessage(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PrivateMessage>(PrivateMessageTable)

    var from by User referencedOn PrivateMessageTable.from
    var to by User referencedOn PrivateMessageTable.to
    var content by PrivateMessageTable.content
    var time by PrivateMessageTable.time
}

object GroupMessageTable : LongIdTable() {
    val from = reference("from", UserTable)
    val group = reference("group", GroupTable)
    val content = text("content")
    val time = datetime("time")
}

class GroupMessage(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupMessage>(GroupMessageTable)

    var from by User referencedOn GroupMessageTable.from
    var group by Group referencedOn GroupMessageTable.group
    var content by GroupMessageTable.content
    var time by GroupMessageTable.time
}