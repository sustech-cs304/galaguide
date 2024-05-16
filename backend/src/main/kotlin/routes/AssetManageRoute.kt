package galaGuide.routes

import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.table.StaticAsset
import galaGuide.table.StaticAssetTable
import galaGuide.util.StaticAssetManager
import io.ktor.http.*
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

        @Serializable
        data class StaticAssetResponse(
            val uuid: String,
            val fileName: String,
        )

        fun StaticAsset.asSerializable() = StaticAssetResponse(
            id.value.toString(),
            fileName
        )

        fun StaticAsset.asRestResponse() = asSerializable().asRestResponse()

        post("upload") {
            val uploader = call.user!!
            var fileName: String? = null
            var fileStream: InputStream? = null
            call.receiveMultipart().forEachPart {
                when (it) {
                    is PartData.FileItem -> {
                        fileName = it.originalFileName
                        fileStream = it.streamProvider()
                    }

                    else -> {}
                }
                it.dispose()
            }

            if (fileStream == null) {
                call.respond(failRestResponseDefault(-2, "No file uploaded"))
                return@post
            }

            kotlin.runCatching {
                call.respond(
                    StaticAssetManager.new(fileStream!!, uploader, fileName)
                        .asRestResponse()
                )
            }.getOrElse {
                call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
            }
        }

        post("{uuid}") {
            val uploader = call.user!!
            val uuid = UUID.fromString(call.parameters["uuid"] ?: run {
                call.respond(failRestResponseDefault(-1, "UUID not provided"))
                return@post
            })
            var fileName: String? = null
            var fileStream: InputStream? = null
            call.receiveMultipart().forEachPart {
                when (it) {
                    is PartData.FileItem -> {
                        fileName = it.originalFileName
                        fileStream = it.streamProvider()
                    }

                    else -> {}
                }
                it.dispose()
            }

            kotlin.runCatching {
                call.respond(
                    StaticAssetManager.change(uuid, uploader, fileStream, fileName)
                        .asRestResponse()
                )
            }.getOrElse {
                call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
            }
        }

        get("{uuid}") {
            val uuid = UUID.fromString(call.parameters["uuid"] ?: run {
                call.respond(failRestResponseDefault(-1, "UUID not provided"))
                return@get
            })

            val asset = StaticAssetManager.query(uuid) ?: run {
                call.respond(failRestResponseDefault(-4, "Asset not found"))
                return@get
            }
            val file = StaticAssetManager.get(uuid) ?: run {
                call.respond(failRestResponseDefault(-5, "File not found"))
                return@get
            }

            kotlin.runCatching {
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment
                        .withParameter(ContentDisposition.Parameters.FileName, asset.fileName)
                        .toString()
                )
                call.respondFile(file)
            }.getOrElse {
                call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
            }
        }

        delete("{uuid}") {
            val uploader = call.user!!
            val uuid = UUID.fromString(call.parameters["uuid"] ?: run {
                call.respond(failRestResponseDefault(-1, "UUID not provided"))
                return@delete
            })

            kotlin.runCatching {
                StaticAssetManager.delete(uuid, uploader)
                call.respond(emptyRestResponse())
            }.getOrElse {
                call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
            }
        }
    }
}