package galaGuide.data

import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll


@Serializable
data class PagingOption(
    val offset: Long = 0,
    val limit: Int = Int.MAX_VALUE,
)

fun Parameters.receivePagingOption() = PagingOption(
    this["offset"]?.toLongOrNull() ?: 0,
    this["limit"]?.toIntOrNull() ?: Int.MAX_VALUE
)

@Serializable
class PagingResult<out T>(
    private val container: List<T>,
    val offset: Long = 0,
    val limit: Int,
    val all: Long = offset + container.size
) : List<T> by container

private fun <T> Iterable<T>.toPagingResult(option: PagingOption, all: Long? = null): PagingResult<T> {
    val list = toList()
    return PagingResult(list, option.offset, option.limit, all ?: (option.offset + option.limit))
}

private fun <T, R> Iterable<T>.toPagingResult(
    option: PagingOption,
    all: Long? = null,
    transform: (T) -> R
): PagingResult<R> {
    val list = map(transform)
    return PagingResult(list, option.offset, option.limit, all ?: (option.offset + option.limit))
}

fun <T> Iterable<T>.page(pagingOption: PagingOption) =
    drop(pagingOption.offset.toInt())
        .take(pagingOption.limit)
        .toPagingResult(pagingOption, count().toLong())

fun <T, R> Iterable<T>.page(pagingOption: PagingOption, transform: (T) -> R) =
    drop(pagingOption.offset.toInt())
        .take(pagingOption.limit)
        .toPagingResult(pagingOption, count().toLong(), transform)

fun Table.page(pagingOption: PagingOption) =
    selectAll().limit(pagingOption.limit, pagingOption.offset)
        .toPagingResult(pagingOption, selectAll().count())

fun <R> Table.page(pagingOption: PagingOption, transform: (ResultRow) -> R) =
    selectAll().limit(pagingOption.limit, pagingOption.offset)
        .toPagingResult(pagingOption, selectAll().count(), transform)

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.page(pagingOption: PagingOption) = all().page(pagingOption)

fun <ID : Comparable<ID>, T : Entity<ID>, R> EntityClass<ID, T>.page(pagingOption: PagingOption, transform: (T) -> R) =
    all().page(pagingOption, transform)