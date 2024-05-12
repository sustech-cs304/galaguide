package galaGuide.table

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object StaticAssetTable : UUIDTable() {
    val event = reference("event", EventTable)
}

class StaticAsset(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StaticAsset>(StaticAssetTable)

    var event by Event referencedOn StaticAssetTable.event
}