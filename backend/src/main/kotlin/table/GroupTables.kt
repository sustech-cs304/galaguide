package galaGuide.table

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
}

object GroupMemberTable : LongIdTable() {
    val groupId = long("group_id").references(GroupTable.id)
    val userId = long("user_id").references(UserTable.id)
}