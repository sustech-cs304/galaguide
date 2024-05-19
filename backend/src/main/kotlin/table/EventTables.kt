package galaGuide.table

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object EventTable : LongIdTable() {
    val title = varchar("title", 128)
    val host = reference("host", UserTable)
    val poster = reference("poster", StaticAssetTable)
    val description = text("description").nullable()
    val cost = long("cost").default(0)
}

class Event(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Event>(EventTable)

    var title by EventTable.title
    var host by User referencedOn EventTable.host
    var poster by StaticAsset referencedOn EventTable.poster
    var description by EventTable.description
    var cost by EventTable.cost

    val assets by StaticAsset via EventAssetTable
    val periods by EventPeriod referrersOn EventPeriodTable.event
}

object EventPeriodTable : LongIdTable() {
    val event = reference("event", EventTable)
    val start = timestamp("start")
    val end = timestamp("end")
}

class EventPeriod(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EventPeriod>(EventPeriodTable)

    var event by Event referencedOn EventPeriodTable.event
    var start by EventPeriodTable.start
    var end by EventPeriodTable.end
}

object EventAssetTable : Table() {
    val event = reference("event", EventTable)
    val asset = reference("asset", StaticAssetTable)
    override val primaryKey = PrimaryKey(event, asset)
}

