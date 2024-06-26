package galaGuide.table

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import galaGuide.util.createThis
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table

object GroupTable : LongIdTable() {
    val name = varchar("name", 128)

    init {
        createThis()
    }
}

class Group(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Group>(GroupTable)

    var name by GroupTable.name
    val members by GroupMember referrersOn GroupMemberTable.group
    val pinnedMessages by GroupMessage via PinnedMessageTable
}

object GroupMemberTable : LongIdTable() {
    val group = reference("group", GroupTable)
    val user = reference("user", UserTable)

    init {
        createThis()
    }
}

class GroupMember(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupMember>(GroupMemberTable)

    val group by Group referencedOn GroupMemberTable.group
    val user by User referencedOn GroupMemberTable.user
}

object PinnedMessageTable : Table() {
    val group = reference("group", GroupTable)
    val message = reference("message", GroupMessageTable)

    init {
        createThis()
    }
}
