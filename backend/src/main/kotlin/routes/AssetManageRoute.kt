package galaGuide.routes

import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.data.receivePagingOption
import galaGuide.resources.user
import galaGuide.resources.userId
import galaGuide.table.StaticAsset
import galaGuide.table.user.User
import galaGuide.util.StaticAssetManager
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.InputStream
import java.util.*

fun Route.routeAssetManage() {
    route("/asset") {
        val logger = KtorSimpleLogger(StaticAssetManager::class.qualifiedName!!)

        @Serializable
        data class StaticAssetResponse(
            val uuid: String,
            val uploaderId: Long,
            val fileName: String,
            val size: Long,
        )

        fun StaticAsset.asSerializable() = StaticAssetResponse(
            id.value.toString(),
            uploader.id.value,
            fileName,
            StaticAssetManager.get(id.value)?.length() ?: 0
        )

        fun StaticAsset.asRestResponse() = asSerializable().asRestResponse()

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

        authenticate("admin") {
            get("/all") {
                val option = call.request.queryParameters.receivePagingOption()
                val result = StaticAssetManager.query(option) { it.asSerializable() }
                call.respond(result.asRestResponse())
            }
        }

        authenticate("user") {
            get {
                call.respond(
                    transaction {
                        StaticAssetManager.queryAll(call.user!!)
                            .map { it.asSerializable() }
                            .asRestResponse()
                    }
                )
            }

            fun handleUpload(uploader: User, data: PartData.FileItem) = kotlin.runCatching {
                val fileName = data.originalFileName
                val fileStream = data.streamProvider()

                StaticAssetManager.new(fileStream, uploader, fileName)
            }.getOrElse {
                logger.error(it)
                null
            }
            post("upload") {
                val uploader = call.user!!
                val uploads = mutableListOf<StaticAssetResponse?>()
                call.receiveMultipart().forEachPart {
                    when (it) {
                        is PartData.FileItem -> {
                            uploads += handleUpload(uploader, it)?.asSerializable()
                        }

                        else -> {}
                    }
                    it.dispose()
                }
                call.respond(uploads.asRestResponse())
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
                }.onFailure {
                    logger.error(it)
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
                }.onFailure {
                    logger.error(it)
                    call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
                }
            }

            get("allocation") {
                val userId = call.userId!!
                call.respond(StaticAssetManager.getAllocation(userId).asRestResponse())
            }
        }
    }
}