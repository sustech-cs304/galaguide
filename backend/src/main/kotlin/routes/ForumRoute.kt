package galaGuide.routes


import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.resources.user
import galaGuide.table.forum.*
import galaGuide.table.user.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

fun Route.routeForum() = authenticate("user") {
    route("/discuss") {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(DiscussTable, TagTable, LikeTable)
        }
        createDiscuss()
        deleteDiscuss()
        getDiscussList()
        getReplyList()
        uploadDiscussReply()
        getSimilarDiscuss()
        updateDiscussLikes()
    }
}

fun Route.getSimilarDiscuss() {
    get("/{discussId}/related") {
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: DiscussId"))
            return@get
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: Discuss does not exist"))
            return@get
        }
        val discussTagsSet = transaction { Tag.find { TagTable.discussId eq discussId }.map { e -> e.name }.toSet() }
        if (discussTagsSet.isEmpty()) {
            call.respond(emptyRestResponse()) // 如果当前讨论没有标签，直接返回空response
            return@get
        }

        val allDiscussWithTags = transaction {
            Discuss.all()
                .filter {
                    it.id.value != discussId && it.belongsToId == 0.toLong() && Tag.find { TagTable.discussId eq it.id }
                        .any { tag -> tag.name in discussTagsSet }
                } // 获取所有和当前讨论具有相同标签的其他讨论
        }

        val sortedDiscusses = transaction {
            allDiscussWithTags.groupBy {
                Tag.find { TagTable.discussId eq it.id }.count { tag -> tag.name in discussTagsSet }
            } // 按照和当前讨论共有标签的数量进行分组
                .toList().sortedByDescending { (count, _) -> count }
                .take(10) // 取共有标签数量前十的讨论
                .flatMap { (_, discusses) -> discusses }
        }
        call.respond(sortedDiscusses.asRestResponse())
    }
}


fun Route.uploadDiscussReply() {
    post<ForumRoute.Reply.Object>("/{discussId}/upload-reply") {
        if (it.equals(null)) {
            call.respond(failRestResponseDefault(-1, "Missing request body"))
            return@post
        }
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: DiscussId"))
            return@post
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: Discuss does not exist"))
            return@post
        }
        val currentUser = call.user
        if (currentUser == null) {
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@post
        }
//        val title = it.title
//        val posterId = it.posterId
//        val content = it.content
//        val createTime = it.time
//        val replyId = DiscussTable.insertAndGetId { reply ->
//            reply[DiscussTable.likes] = 0
//            reply[DiscussTable.title] = title
//            reply[DiscussTable.content] = content
//            reply[DiscussTable.createTime] = Instant.ofEpochSecond(createTime)
//            reply[DiscussTable.posterId] = posterId
//            reply[DiscussTable.belongsToId] = discuss.id
//        }
        newSuspendedTransaction {
            val reply = Discuss.new {
                title = it.title
                content = it.content
                createTime = Instant.ofEpochSecond(Date().time)
                poster = currentUser
                belongsToId = discuss.id.value
                likes = 0
            }
            kotlin.runCatching {
                call.respond(
                    reply.asRestResponse()
                )
            }
        }
    }
}

fun Route.getReplyList() {
    get("/{discussId}") {
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: DiscussId"))
            return@get
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
            return@get
        }
        // 获取该帖子的所有回复并按时间排序
        val replies = transaction {
            Discuss.find { DiscussTable.belongsToId eq discussId }.toList().sortedBy { it.createTime }
        }

        call.respond((listOf(discuss) + replies).asRestResponse())
    }
}


fun Route.getDiscussList() {
    get("/discuss-list") {
        newSuspendedTransaction {
            val allDiscusses = Discuss.find { DiscussTable.belongsToId eq 0 }
            call.respond(allDiscusses.asRestResponse())
        }
    }
}

fun Route.deleteDiscuss() {
    delete("/discuss/{discussId}") {
        if (call.user == null) {
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@delete
        }
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: DiscussId"))
            return@delete
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
            return@delete
        }
        if (discuss.poster != call.user) {
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Permission Denied"))
            return@delete
        }
        transaction {
            Discuss.find { DiscussTable.belongsToId eq discussId }.forEach { it.delete() }
        }
        transaction { Discuss.find { DiscussTable.id eq discussId }.forEach { it.delete() } }

        call.respond(emptyRestResponse("Operation Success: Delete"))
    }
}

fun Route.createDiscuss() {
    post<ForumRoute.Reply.Object>("/create-discuss") {
        newSuspendedTransaction {
            val currentUser = call.user
            if (currentUser == null) {
                call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
                return@newSuspendedTransaction
            }
            val discuss = Discuss.new {
                title = it.title
                content = it.content
                poster = currentUser
                belongsToId = 0
                createTime = Instant.ofEpochSecond(Date().time)
                likes = 0
            }
            call.respond(discuss.asRestResponse())
        }
    }
}

fun Route.updateDiscussLikes() {
    post("/{discuss_id}/like/{id}") {
        val discussId = call.parameters["discuss_id"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: DiscussId"))
            return@post
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
            return@post
        }
        val id = call.parameters["id"]?.toLong()
        if (id == null) {
            call.respond(failRestResponseDefault(-1, "Missing argument: UserId"))
            return@post
        }
        val user = transaction { User.findById(id) }
        if (user == null) {
            call.respond(failRestResponseDefault(-2, "Wrong argument: UserId"))
            return@post
        }
        val likeRecord =
            transaction { LikeTable.select { (LikeTable.likerId eq id) and (LikeTable.discussId eq discussId) } }
        newSuspendedTransaction {
            if (likeRecord.empty()) {
                LikeTable.insert { record ->
                    record[likerId] = id
                    record[LikeTable.discussId] = discussId
                }
                discuss.likes.plus(1)
                call.respond(discuss.likes.asRestResponse("Operation Success: Like"))
            } else {
                LikeTable.deleteWhere { (likerId eq id) and (LikeTable.discussId eq discussId) }
                discuss.likes.plus(-1)
                call.respond(discuss.likes.asRestResponse())
            }
        }
    }
}

class ForumRoute {
    class Reply {
        @Serializable
        data class Object(
            val title: String,
            val content: String,
        )
    }
}