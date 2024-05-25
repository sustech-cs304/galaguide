package galaGuide.table.user

import galaGuide.table.EventTable
import galaGuide.util.createThis
import org.jetbrains.exposed.sql.Table

object UserFavoriteEventTable : Table() {
    val user = reference("user", UserTable)
    val event = reference("event", EventTable)

    override val primaryKey = PrimaryKey(user, event)

    init {
        createThis()
    }
}