package galaGuide.table

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object StaticAssetTable : UUIDTable() {
    val uploadTime = datetime("uploadTime").clientDefault { LocalDateTime.now() }
}

class StaticAsset(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StaticAsset>(StaticAssetTable)
}