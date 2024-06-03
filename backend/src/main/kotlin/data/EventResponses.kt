package galaGuide.data

import galaGuide.table.Event
import galaGuide.table.EventPeriod
import io.ktor.util.logging.*
import kotlinx.serialization.Serializable

@Serializable
data class EventPeriodDetail(
    val id: Long? = null,
    val start: Long? = null,
    val end: Long? = null,
)

fun EventPeriod.asDetail() = EventPeriodDetail(
    id.value,
    start.epochSecond,
    end.epochSecond,
)

@Serializable
data class EventDetail(
    val id: Long? = null,
    val title: String? = null,
    val hostId: Long? = null,
    val posterId: String? = null,
    val description: String? = null,
    val cost: Long? = null,
    val assetIds: List<String>? = null,
    val periods: List<EventPeriodDetail>? = null,
    val category: String? = null,
)

@Serializable
data class EventFilter(
    val category: List<String>? = null,
    val startDate: Long,
    val endDate: Long,
    val status: String? = null,
    val maxPrice: Long,
    val minPrice: Long
)

data class EventCount(
    val event: Event,
    val count: Int
)
val logger = KtorSimpleLogger("EventLog")
fun Event.asDetail() = EventDetail(
    id.value,
    title,
    host.id.value,
    poster.id.value.toString(),
    description,
    cost,
    assets.map { it.id.value.toString() },
    periods.map { it.asDetail() },
    category,
)

fun Event.asRestResponse() = asDetail().asRestResponse()
fun List<Event>.asRestResponse() = map { it.asDetail() }.asRestResponse()