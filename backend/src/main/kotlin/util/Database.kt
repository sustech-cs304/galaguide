package galaGuide.util

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

fun <T : Table> T.createThis() = transaction {
    SchemaUtils.createMissingTablesAndColumns(this@createThis)
}