package galaGuide.table

import galaGuide.table.user.User
import galaGuide.table.user.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object GroupTable : LongIdTable() {
    val name = varchar("name", 128)
}

class Group(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Group>(GroupTable)

    var name by GroupTable.name
    val members by GroupMember referrersOn GroupMemberTable.group
}

object GroupMemberTable : LongIdTable() {
    val group = reference("group", GroupTable)
    val user = reference("user", UserTable)
}

class GroupMember(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupMember>(GroupMemberTable)

    val group by Group referencedOn GroupMemberTable.group
    val user by User referencedOn GroupMemberTable.user
}
