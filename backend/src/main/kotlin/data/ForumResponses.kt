package galaGuide.data

import galaGuide.table.forum.Discuss
import galaGuide.table.forum.TagTable
import kotlinx.serialization.Serializable

@Serializable
data class DiscussDetail(
    val id: Long,
    val title: String = "",
    val content: String? = null,
    val posterId: Long? = null,
    val posterName: String? = null,
    val likes: Long = 0,
    val time: Long? = null,
    val belongsToId: Long,
)

fun Discuss.asDetail() = DiscussDetail(
    id.value,
    title,
    content,
    poster.id.value,
    poster.name,
    likes,
    createTime.epochSecond,
    belongsToId,
)

fun Discuss.asRestResponse() = asDetail().asRestResponse()
fun List<Discuss>.asRestResponse() = map { e -> e.asDetail() }.asRestResponse()
