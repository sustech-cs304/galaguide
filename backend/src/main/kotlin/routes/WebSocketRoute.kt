package galaGuide.routes

import galaGuide.data.*
import galaGuide.resources.userId
import galaGuide.table.GroupMemberTable
import galaGuide.table.GroupMessage
import galaGuide.table.PrivateMessage
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.logging.*
import io.ktor.websocket.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object WebsocketManager {
    private val connected = Collections.synchronizedMap(mutableMapOf<Long, WebSocketServerSession>())
    private val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)

    suspend fun handleSession(id: Long, session: WebSocketServerSession) {
        try {
            connected[id] = session

            val loginEvent = UserLoginEvent(id)
            connected.values.forEach {
                it.sendSerialized(loginEvent)
            }

            while (true) {
                val event = try {
                    session.receiveDeserialized<WebSocketEvent>()
                } catch (e: Exception) {
                    logger.error("Error receiving event from user $id", e)
                    continue
                }
                logger.info("Received event from user $id: $event")
                when (event) {
                    is PrivateMessageEvent -> {
                        val messageEvent = event.copy(from = id, time = Clock.System.now())
                        transaction {
                            PrivateMessage.new {
                                from = id
                                to = event.to
                                content = event.content
                                time = messageEvent.time
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .toJavaLocalDateTime()
                            }
                        }

                        connected[event.to]?.sendSerialized(messageEvent)
                    }

                    is GroupMessageEvent -> {
                        val messageEvent = event.copy(from = id, time = Clock.System.now())

                        val ids = transaction {
                            GroupMessage.new {
                                from = id
                                group = event.group
                                content = event.content
                                time = messageEvent.time
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .toJavaLocalDateTime()

                            }

                            GroupMemberTable.select { GroupMemberTable.groupId eq messageEvent.group }
                                .map { it[GroupMemberTable.userId] }
                        }

                        ids.mapNotNull { connected[it] }
                            .forEach {
                                it.sendSerialized(messageEvent)
                            }
                    }

                    else -> {}
                }
            }
        } finally {
            connected.remove(id)

            val logoutEvent = UserLogoutEvent(id)
            connected.values.forEach {
                it.sendSerialized(logoutEvent)
            }
        }
    }
}

fun Route.routeWebSocket() = authenticate("user") {
    webSocket("/ws") {
        val userId = call.userId ?: run {
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "please login first"))
            return@webSocket
        }

        WebsocketManager.handleSession(userId, this)
    }
}