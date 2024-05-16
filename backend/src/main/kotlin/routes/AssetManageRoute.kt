package galaGuide.routes

import galaGuide.data.asRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.table.StaticAssetTable
import galaGuide.util.StaticAssetManager
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.InputStream
import java.util.*

fun Route.routeAssetManage() = authenticate("user") {
    route("/asset") {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(StaticAssetTable)
        }

        post("upload") {
            val uploader = call.user ?: run {
                call.respond(failRestResponseDefault(-1, "Login required"))
                return@post
            }
            var fileName: String? = null
            var fileStream: InputStream? = null
            var fileUuid: UUID? = null
            call.receiveMultipart().forEachPart {
                when (it) {
                    is PartData.FormItem -> {
                        when (it.name) {
                            "uuid" -> fileUuid = UUID.fromString(it.value)
                        }
                    }

                    is PartData.FileItem -> {
                        fileName = it.originalFileName
                        fileStream = it.streamProvider()
                    }

                    else -> {}
                }
                it.dispose()
            }

            kotlin.runCatching {
                val asset = StaticAssetManager.upload(uploader, fileStream, fileName, fileUuid)

                @Serializable
                data class StaticAssetResponse(
                    val uuid: String,
                    val fileName: String,
                )

                call.respond(
                    StaticAssetResponse(
                        asset.id.value.toString(),
                        asset.fileName
                    ).asRestResponse()
                )
            }.getOrElse {
                call.respond(failRestResponseDefault(-2, it.message ?: "Unknown error"))
            }
        }
    }
}