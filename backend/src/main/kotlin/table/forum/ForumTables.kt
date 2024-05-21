package galaGuide.table.forum

import galaGuide.table.user.UserTable
import galaGuide.table.user.User
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

// 帖子表
object DiscussTable : LongIdTable() {
    val title = varchar("title", 100)
    val content = text("content")
    val posterId = reference("poster_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val createTime = timestamp("create_time")
    val likes = long("likes")
    val belongsToId = reference("belongs_to_id", id, onDelete = ReferenceOption.CASCADE)
}

// 标签表
object TagTable : LongIdTable() {
    val name = varchar("name", 50)
}

// 中间表
object DiscussTagTable : Table() {
    val discussId = reference("discuss_id", DiscussTable)
    val tagId = reference("tag_id", TagTable)
    override val primaryKey = PrimaryKey(discussId)
}

// 帖子表实体类
class Discuss(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Discuss>(DiscussTable)

    var title by DiscussTable.title
    var content by DiscussTable.content
    var poster by User referencedOn DiscussTable.posterId
    var belongsTo by User referencedOn DiscussTable.belongsToId
    var createTime by DiscussTable.createTime
    var likes by DiscussTable.likes
    val tags by Tag via DiscussTagTable
}

// 标签实体类
class Tag(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Tag>(TagTable)

    var name by TagTable.name
    val discusses by Discuss referrersOn DiscussTagTable.tagId
}

object LikeTable : Table() {
    val likerId = reference("liker_id", UserTable)
    val discussId = reference("discuss_id", DiscussTable)
    override val primaryKey = PrimaryKey(likerId, discussId)
}