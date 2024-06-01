package galaGuide.routes

import galaGuide.data.*
import galaGuide.resources.userId
import galaGuide.table.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeContact() {
    authenticate("user") {
        route("/contact") {
            routePrivate()
            routeGroup()
        }
    }
}

private fun Route.routePrivate() {
    route("/private") {
        get {
            val users = transaction {
                with(PrivateMessageTable) {
                    select {
                        (from eq call.userId) or (to eq call.userId)
                    }.flatMap {
                        listOf(it[from], it[to])
                    }.map {
                        it.value
                    }
                }
            }

            call.respond(users.asRestResponse())
        }

        route("/{from}") {
            get("/msg") {
                val from = call.parameters["from"]?.toLongOrNull() ?: run {
                    call.respond(failRestResponseDefault(-1, "Invalid from user id"))
                    return@get
                }
                val to = call.userId!!
                val option = call.request.queryParameters.receivePagingOption()

                val messages = transaction {
                    PrivateMessage.find {
                        (PrivateMessageTable.from eq from) and
                                (PrivateMessageTable.to eq to)
                    }.page(option) {
                        it.asDetail()
                    }
                }

                call.respond(messages.asRestResponse())
            }
        }
    }
}

private fun Route.routeGroup() {
    route("/group") {
        get {
            val groups = transaction {
                GroupMember.find { GroupMemberTable.user eq call.userId!! }
                    .map { it.group.asDetail() }
            }
            call.respond(groups.asRestResponse())
        }

        route("/{group}") {
            get {
                val groupId = call.parameters["group"]?.toLongOrNull() ?: run {
                    call.respond(failRestResponseDefault(-1, "Invalid group id"))
                    return@get
                }

                val group = transaction {
                    Group.findById(groupId)
                } ?: run {
                    call.respond(failRestResponseDefault(-2, "Group not found"))
                    return@get
                }

                call.respond(group.asRestResponse())
            }

            get("/msg") {
                val group = call.parameters["group"]?.toLongOrNull() ?: run {
                    call.respond(failRestResponseDefault(-1, "Invalid group id"))
                    return@get
                }

                val option = call.request.queryParameters.receivePagingOption()

                transaction {
                    GroupMemberTable.select {
                        (GroupMemberTable.group eq group) and
                                (GroupMemberTable.user eq call.userId!!)
                    }.firstOrNull()
                } ?: run {
                    call.respond(failRestResponseDefault(-2, "You are not a member of this group"))
                    return@get
                }

                val messages = transaction {
                    GroupMessage.find {
                        GroupMessageTable.group eq group
                    }.page(option) {
                        it.asDetail()
                    }
                }

                call.respond(messages.asRestResponse())
            }
        }
    }
}