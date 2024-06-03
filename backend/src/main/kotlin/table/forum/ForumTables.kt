package galaGuide.table.forum

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import galaGuide.util.createThis
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

// 帖子表
object DiscussTable : LongIdTable() {
    val title = varchar("title", 100)
    val content = text("content")
    val posterId = reference("poster_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val createTime = timestamp("create_time")
    val likes = long("likes")
    val belongsToId = long("belongsToId")

    init {
        createThis()
    }
}

// 标签表
object TagTable : LongIdTable() {
    val discussId = reference("discuss_id", DiscussTable, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 50)

    init {
        createThis()
    }
}

// 帖子表实体类
class Discuss(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Discuss>(DiscussTable)

    var title by DiscussTable.title
    var content by DiscussTable.content
    var poster by User referencedOn DiscussTable.posterId
    var belongsToId by DiscussTable.belongsToId
    var createTime by DiscussTable.createTime
    var likes by DiscussTable.likes
}

// 标签实体类
class Tag(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Tag>(TagTable)
    val discuss by Discuss referencedOn TagTable.discussId
    val name by TagTable.name
}

object LikeTable : Table() {
    val likerId = reference("liker_id", UserTable)
    val discussId = reference("discuss_id", DiscussTable)
    override val primaryKey = PrimaryKey(likerId, discussId)

    init {
        createThis()
    }
}