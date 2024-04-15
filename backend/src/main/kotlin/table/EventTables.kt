package galaGuide.table

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

object EventTable : LongIdTable() {
    val title = varchar("title", 128)
    val host = reference("host", UserTable)
    val poster = uuid("poster")
    val description = text("description")
}

class Event(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Event>(EventTable)

    var title by EventTable.title
    var host by User referencedOn EventTable.host
    var poster by EventTable.poster
    var description by EventTable.description

    val images by EventImage referrersOn EventImageTable.event
    val periods by EventPeriod referrersOn EventPeriodTable.event
}

object EventImageTable : UUIDTable() {
    val event = reference("event", EventTable)
}

class EventImage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EventImage>(EventImageTable)

    var event by Event referencedOn EventImageTable.event
}

object EventPeriodTable : LongIdTable() {
    val event = reference("event", EventTable)
    val start = datetime("start")
    val end = datetime("end")
}

class EventPeriod(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EventPeriod>(EventPeriodTable)

    var event by Event referencedOn EventPeriodTable.event
    var start by EventPeriodTable.start
    var end by EventPeriodTable.end
}

