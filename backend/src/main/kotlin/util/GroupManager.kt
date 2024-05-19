package galaGuide.util

import galaGuide.table.Group
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction

object GroupManager {
    @Serializable
    data class GroupCreateOption(
        val name: String
    )

    fun createGroup(option: GroupCreateOption) = transaction {
        Group.new {
            name = option.name
        }
    }
}