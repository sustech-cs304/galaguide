package galaGuide.util

import galaGuide.table.StaticAsset
import galaGuide.table.user.User
import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.InputStream
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.outputStream

object StaticAssetManager {
    const val fileStorePath = "user-static"

    private val logger = KtorSimpleLogger(StaticAssetManager::class.qualifiedName!!)

    private fun putFile(stream: InputStream, fileName: String) {
        Paths.get(fileStorePath, fileName).outputStream().use { to ->
            stream.use { from ->
                from.copyTo(to)
            }
        }
    }

    fun new(stream: InputStream, uploader: User, fileName: String? = null, uuid: UUID = UUID.randomUUID()) =
        transaction {
            StaticAsset.findById(uuid)?.let {
                error("Asset $uuid already exists")
            }

            putFile(stream, uuid.toString())

            logger.info("[StaticAsset] New asset $uuid by ${uploader.name} ($fileName)")

            return@transaction StaticAsset.new(uuid) {
                this.fileName = fileName ?: uuid.toString()
                this.uploader = uploader
            }
        }

    fun change(uuid: UUID, uploader: User? = null, stream: InputStream? = null, fileName: String? = null) =
        transaction {
            val asset = StaticAsset.findById(uuid) ?: error("Asset $uuid not found")
            asset.takeUnless { uploader != null && asset.uploader != uploader } ?: error("Uploader mismatch")

            stream?.let {
                putFile(stream, uuid.toString())
            }
            fileName?.let {
                asset.fileName = fileName
            }

            logger.info("[StaticAsset] Change asset $uuid by ${uploader?.name} ($fileName)")

            return@transaction asset
        }

    fun upload(uploader: User, stream: InputStream? = null, fileName: String? = null, uuid: UUID? = null) =
        transaction {
            uuid?.let {
                return@transaction change(it, uploader, stream, fileName)
            }

            return@transaction new(stream ?: error("No file provided"), uploader, fileName)
        }
}