package galaGuide.table.user

import galaGuide.table.Event
import galaGuide.table.StaticAsset
import galaGuide.table.StaticAssetTable
import io.ktor.server.auth.*
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import java.security.MessageDigest

object UserTable : LongIdTable() {
    val name = varchar("name", 32)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
    val guiro = long("guiro").default(0)
    val emailVerified = bool("emailVerified").default(false)
    val avatar = reference("avatar", StaticAssetTable).nullable()
}

class User(id: EntityID<Long>) : Principal, LongEntity(id) {
    companion object : LongEntityClass<User>(UserTable)

    var name by UserTable.name
    private var password by UserTable.password
    var email by UserTable.email
    var guiro by UserTable.guiro
    var emailVerified by UserTable.emailVerified
    var avatar by StaticAsset optionalReferencedOn UserTable.avatar

    val favoriteEvents by Event via UserFavoriteEventTable

    fun checkPassword(pwd: String) = MessageDigest.getInstance("SHA-256")
        .digest(pwd.toByteArray())
        .joinToString("") { byte ->
            "%02x".format(byte)
        } == password

    fun changePassword(newPwd: String) {
        password = MessageDigest.getInstance("SHA-256")
            .digest(newPwd.toByteArray())
            .joinToString("") { byte ->
                "%02x".format(byte)
            }
    }
}