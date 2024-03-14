package galaGuide.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("event")
sealed class WebSocketEvent {
    abstract val time: Instant
}

@Serializable
@SerialName("login")
data class UserLoginEvent(val id: Long, override val time: Instant = Clock.System.now()) : WebSocketEvent()

@Serializable
@SerialName("logout")
data class UserLogoutEvent(val id: Long, override val time: Instant = Clock.System.now()) : WebSocketEvent()

@Serializable
@SerialName("private")
data class PrivateMessageEvent(
    val from: Long? = null,
    val to: Long,
    val content: String,
    override val time: Instant = Clock.System.now()
) : WebSocketEvent()

@Serializable
@SerialName("group")
data class GroupMessageEvent(
    val from: Long? = null,
    val group: Long,
    val content: String,
    override val time: Instant = Clock.System.now()
) : WebSocketEvent()