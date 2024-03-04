package galaGuide.table

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable() {
    val name = varchar("name", 32)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
}