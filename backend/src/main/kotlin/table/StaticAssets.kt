package galaGuide.table

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object StaticAssetTable : UUIDTable() {
    val uploadTime = datetime("uploadTime").clientDefault { LocalDateTime.now() }
    val fileName = varchar("fileName", 128)
    val uploader = reference("uploader", UserTable)
}

class StaticAsset(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StaticAsset>(StaticAssetTable)

    var uploadTime by StaticAssetTable.uploadTime
    var fileName by StaticAssetTable.fileName
    var uploader by User referencedOn StaticAssetTable.uploader
}