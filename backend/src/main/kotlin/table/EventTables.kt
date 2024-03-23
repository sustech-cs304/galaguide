package galaGuide.table

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
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

    val images by EventImage referrersOn EventImagesTable.event
}

object EventImagesTable : UUIDTable() {
    val event = reference("event", EventTable)
}

class EventImage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EventImage>(EventImagesTable)

    var event by Event referencedOn EventImagesTable.event
}

