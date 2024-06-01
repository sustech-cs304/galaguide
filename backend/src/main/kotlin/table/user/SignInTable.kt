package galaGuide.table.user

import galaGuide.util.createThis
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

object UserSignInTable : IdTable<Long>() {
    val user = reference("user", UserTable)
    val nextSignIn = datetime("nextSignIn").clientDefault { LocalDateTime.MIN }

    override val id get() = user
    override val primaryKey = PrimaryKey(user)

    init {
        createThis()
    }
}

class UserSignIn(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserSignIn>(UserSignInTable) {
        val interval = 1.days.toJavaDuration()
        val guiroEachSignIn = 100
    }

    val user by User referencedOn UserSignInTable.user
    var nextSignIn by UserSignInTable.nextSignIn

    fun signIn() = (nextSignIn <= LocalDateTime.now()).also {
        if (it) {
            nextSignIn = LocalDateTime.now() + interval
            user.guiro += guiroEachSignIn
        }
    }
}