package galaGuide.table.user

import galaGuide.table.Event
import galaGuide.table.StaticAsset
import galaGuide.table.StaticAssetTable
import galaGuide.util.createThis
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
    val background = reference("background", StaticAssetTable).nullable()
    val intro = text("intro").default("This user is too lazy to write an introduction.")

    init {
        createThis()
    }
}

class User(id: EntityID<Long>) : Principal, LongEntity(id) {
    companion object : LongEntityClass<User>(UserTable) {
        private val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")

        fun checkNameAvailable(name: String) = name.length <= 32 && find { UserTable.name eq name }.empty()

        fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)

        fun checkPasswordAvailable(password: String) = password.length in 6..128
    }

    var name by UserTable.name
    private var password by UserTable.password
    var email by UserTable.email
    var guiro by UserTable.guiro
    var emailVerified by UserTable.emailVerified
    var avatar by StaticAsset optionalReferencedOn UserTable.avatar
    var background by StaticAsset optionalReferencedOn UserTable.background
    var intro by UserTable.intro

    val favoriteEvents by Event via UserFavoriteEventTable
    val historyEvents by Event via UserHistoryEventTable

    val uploadedAssets by StaticAsset referrersOn StaticAssetTable.uploader

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