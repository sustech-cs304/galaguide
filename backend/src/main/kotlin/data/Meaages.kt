package galaGuide.data

import galaGuide.table.GroupMessage
import galaGuide.table.PrivateMessage
import kotlinx.serialization.Serializable
import java.time.ZoneOffset

private val zone = ZoneOffset.of("+8")

@Serializable
data class PrivateMessageDetail(
    val from: Long,
    val to: Long,
    val content: String,
    val time: Long,
)

fun PrivateMessage.asDetail() = PrivateMessageDetail(
    from.id.value,
    to.id.value,
    content,
    time.toEpochSecond(zone)
)

@Serializable
data class GroupMessageDetail(
    val from: Long,
    val group: Long,
    val content: String,
    val time: Long,
)

fun GroupMessage.asDetail() = GroupMessageDetail(
    from.id.value,
    group.id.value,
    content,
    time.toEpochSecond(zone)
)