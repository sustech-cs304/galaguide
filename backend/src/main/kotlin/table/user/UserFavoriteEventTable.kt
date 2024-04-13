package galaGuide.table.user

import galaGuide.table.EventTable
import org.jetbrains.exposed.sql.Table

object UserFavoriteEventTable : Table() {
    val user = reference("user", UserTable)
    val event = reference("event", EventTable)

    override val primaryKey = PrimaryKey(user, event)
}