package galaGuide.table

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object PrivateMessageTable : LongIdTable() {
    val from = long("from").references(UserTable.id)
    val to = long("to").references(UserTable.id)
    val content = text("content")
    val time = datetime("time")
}

class PrivateMessage(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PrivateMessage>(PrivateMessageTable)

    var from by PrivateMessageTable.from
    var to by PrivateMessageTable.to
    var content by PrivateMessageTable.content
    var time by PrivateMessageTable.time
}

object GroupMessageTable : LongIdTable() {
    val from = long("from").references(UserTable.id)
    val group = long("group")
    val content = text("content")
    val time = datetime("time")
}

class GroupMessage(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupMessage>(GroupMessageTable)

    var from by GroupMessageTable.from
    var group by GroupMessageTable.group
    var content by GroupMessageTable.content
    var time by GroupMessageTable.time
}