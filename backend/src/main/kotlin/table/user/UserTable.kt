package galaGuide.table.user

import galaGuide.table.Event
import galaGuide.table.StaticAsset
import galaGuide.table.StaticAssetTable
import galaGuide.util.createThis
import io.ktor.server.auth.*
import io.ktor.util.logging.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import java.security.MessageDigest
import java.util.*

private val logger = KtorSimpleLogger(UserTable::class.qualifiedName!!)

@Serializable(UserRole.Serializer::class)
enum class UserRole(val id: Int) {
    USER(1), ADMIN(2);

    companion object {
        private val map = entries.associateBy(UserRole::id)
        operator fun get(id: Int) = map[id]
    }

    object Serializer : KSerializer<UserRole> {
        override val descriptor = PrimitiveSerialDescriptor("UserRole", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): UserRole {
            return UserRole[decoder.decodeInt()] ?: throw IllegalArgumentException("Invalid user role id")
        }

        override fun serialize(encoder: Encoder, value: UserRole) {
            encoder.encodeInt(value.id)
        }
    }
}

object UserTable : LongIdTable() {
    val name = varchar("name", 32)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
    val guiro = long("guiro").default(500)
    val emailVerified = bool("emailVerified").default(false)
    val avatar = reference("avatar", StaticAssetTable).nullable()
    val background = reference("background", StaticAssetTable).nullable()
    val intro = text("intro").default("This user is too lazy to write an introduction.")
    val role = customEnumeration(
        "role",
        "varchar(16)",
        { UserRole.valueOf(it as String) },
        { it.name }
    ).default(UserRole.USER)

    init {
        createThis {
            User.tryInitRoot()
        }
    }
}

class User(id: EntityID<Long>) : Principal, LongEntity(id) {
    companion object : LongEntityClass<User>(UserTable) {
        private val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")

        fun checkNameAvailable(name: String) = name.length <= 32 && find { UserTable.name eq name }.empty()

        fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)

        fun checkPasswordAvailable(password: String) = password.length in 6..128

        fun tryInitRoot() {
            if (User.find { UserTable.role eq UserRole.ADMIN }.any()) {
                return
            }

            logger.info("Detected no admin user, will create a new admin user.")
            val randomName = (1..8).map {
                ('a'..'z').random()
            }.joinToString("")
            val rootName = "root-$randomName"
            val password = UUID.randomUUID().toString().replace("-", "")
            val email = "noreply@galaguide.com"

            new {
                name = rootName
                changePassword(password)
                this.email = email
                emailVerified = true
                role = UserRole.ADMIN
            }

            logger.info("Name: $rootName")
            logger.info("Password: $password")
            logger.info("Email: $email")
        }
    }

    var name by UserTable.name
    private var password by UserTable.password
    var email by UserTable.email
    var guiro by UserTable.guiro
    var emailVerified by UserTable.emailVerified
    var avatar by StaticAsset optionalReferencedOn UserTable.avatar
    var background by StaticAsset optionalReferencedOn UserTable.background
    var intro by UserTable.intro
    var role by UserTable.role

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