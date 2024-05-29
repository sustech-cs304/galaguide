package galaGuide.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import galaGuide.data.*
import galaGuide.table.GroupMemberTable
import galaGuide.table.GroupMessageTable
import galaGuide.table.PrivateMessageTable
import galaGuide.table.user.User
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.logging.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.collections.set

object WebsocketManager {
    private val connected = Collections.synchronizedMap(mutableMapOf<Long, WebSocketServerSession>())
    private val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)

    suspend fun handleSession(id: Long, session: WebSocketServerSession) {
        try {
            connected[id] = session

            val loginEvent: WebSocketEvent = UserLoginEvent(id)
            connected.values.forEach {
                it.sendSerialized(loginEvent)
            }

            while (true) {
                val event = try {
                    session.receiveDeserialized<WebSocketEvent>()
                } catch (e: ClosedReceiveChannelException) {
                    break
                } catch (e: Exception) {
                    logger.error("Error receiving event from user $id", e)
                    continue
                }
                logger.info("Received event from user $id: $event")
                when (event) {
                    is PrivateMessageEvent -> {
                        val messageEvent = event.copy(from = id, time = Clock.System.now())
                        transaction {
                            PrivateMessageTable.insert {
                                it[from] = id
                                it[to] = event.to
                                it[content] = event.content
                                it[time] = messageEvent.time
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .toJavaLocalDateTime()
                            }
                        }

                        connected[event.to]?.sendSerialized(messageEvent)
                    }

                    is GroupMessageEvent -> {
                        val messageEvent = event.copy(from = id, time = Clock.System.now())

                        val ids = transaction {
                            GroupMessageTable.insert {
                                it[from] = id
                                it[group] = event.group
                                it[content] = event.content
                                it[time] = messageEvent.time
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .toJavaLocalDateTime()
                            }

                            GroupMemberTable.select { GroupMemberTable.group eq messageEvent.group }
                                .map { it[GroupMemberTable.user].value }
                        }

                        ids.mapNotNull { connected[it] }
                            .forEach {
                                it.sendSerialized(messageEvent)
                            }
                    }

                    is UserLogoutEvent -> break

                    else -> {}
                }
            }
        } finally {
            connected.remove(id)
            session.close()

            val logoutEvent: WebSocketEvent = UserLogoutEvent(id)
            connected.values.forEach {
                it.sendSerialized(logoutEvent)
            }
        }
    }
}

fun Route.routeWebSocket() {
    val secret = environment!!.config.property("user-jwt.secret").getString()
    val issuer = environment!!.config.property("user-jwt.issuer").getString()
    val audience = environment!!.config.property("user-jwt.audience").getString()

    val verifier = JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)

    webSocket("/ws") {
        val token = (receiveDeserialized<WebSocketEvent>() as? AuthEvent)?.token ?: run {
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Invalid token"))
            return@webSocket
        }
        val user = kotlin.runCatching {
            transaction {
                User[verifier.verify(token)?.getClaim("id")?.asLong() ?: -1]
            }
        }.getOrElse {
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Invalid token"))
            return@webSocket
        }

        WebsocketManager.handleSession(user.id.value, this)
    }
}