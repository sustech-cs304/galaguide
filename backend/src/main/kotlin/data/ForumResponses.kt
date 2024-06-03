package galaGuide.data

import galaGuide.table.forum.Discuss
import galaGuide.table.forum.Tag
import galaGuide.table.forum.TagTable
import kotlinx.serialization.Serializable

@Serializable
data class DiscussDetail(
    val title: String = "",
    val content: String? = null,
    val posterId: Long? = null,
    val posterName: String? = null,
    val likes: Long = 0,
    val tags: List<String> = emptyList(),
    val time: Long? = null,
    val belongsToId: Long? = null
)

fun Discuss.asDetail() = DiscussDetail(
    title,
    content,
    poster.id.value,
    poster.name,
    likes,
    Tag.find { TagTable.discussId eq id }.map { tag -> tag.name },
    createTime.epochSecond,
    belongsToId
)

fun Discuss.asRestResponse() = asDetail().asRestResponse()
fun List<Discuss>.asRestResponse() = map { discuss -> discuss.asDetail() }.asRestResponse()

