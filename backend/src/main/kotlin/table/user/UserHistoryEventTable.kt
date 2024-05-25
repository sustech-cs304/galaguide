package galaGuide.table.user

import galaGuide.table.EventTable
import galaGuide.util.createThis
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserHistoryEventTable : Table() {
    val user = reference("user", UserTable)
    val event = reference("event", EventTable)
    val time = datetime("time").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(user, event)

    init {
        createThis()
    }
}