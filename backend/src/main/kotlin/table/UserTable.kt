package galaGuide.table

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable() {
    val name = varchar("name", 32)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
}

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(UserTable)

    var name by UserTable.name
    var password by UserTable.password
    var email by UserTable.email
}