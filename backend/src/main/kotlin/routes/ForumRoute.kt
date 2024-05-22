package galaGuide.routes


import galaGuide.data.asDetail
import galaGuide.data.asRestResponse
import galaGuide.data.emptyRestResponse
import galaGuide.data.failRestResponseDefault
import galaGuide.table.forum.*
import galaGuide.table.user.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

fun Route.routeForum() = authenticate("user") {
    route("/discuss") {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(DiscussTable, TagTable, DiscussTagTable, LikeTable)
        }
        createDiscuss()
//        deleteDiscuss()
        getDiscussList()
        getReplyList()
//        updateDiscussAccessHistory()
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

        val discussTags = transaction { discuss.tags.map { it.name }.toSet() } // 获取当前讨论的所有标签
        if (discussTags.isEmpty()) {
            call.respond(emptyRestResponse()) // 如果当前讨论没有标签，直接返回空response
            return@get
        }

        val allDiscussWithTags = transaction {
            Discuss.all()
                .filter { it.id.value != discussId && it.id.value == it.belongsToId.value && it.tags.any { tag -> tag.name in discussTags } } // 获取所有和当前讨论具有相同标签的其他讨论
        }

        val sortedDiscusses =
            allDiscussWithTags.groupBy { it.tags.count { tag -> tag.name in discussTags } } // 按照和当前讨论共有标签的数量进行分组
                .toList().sortedByDescending { (count, _) -> count }
                .take(10) // 取共有标签数量前十的讨论
                .flatMap { (_, discusses) -> discusses }

        call.respond(sortedDiscusses.asRestResponse())
    }
}


fun Route.uploadDiscussReply() {
    post<ForumRoute.Reply.Object>("/{discussId}/upload-reply") {
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
        val currentUser = call.authentication.principal<User>()
        if (currentUser == null) {
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@post
        }
        if (currentUser.id.value != it.posterId) {
            call.respond(failRestResponseDefault(-3, "Wrong Authentic: Access Denied"))
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
        val reply = Discuss.new {
            title = it.title
            content = it.content
            createTime = Instant.ofEpochSecond(it.time)
            poster = currentUser
            belongsToId = discuss.id
            likes = 0
        }
        kotlin.runCatching { call.respond(reply.asDetail().asRestResponse("Operator Success: Create reply with id: ${reply.id}$")) }
    }
}

fun Route.updateDiscussAccessHistory() {
    TODO("Not yet implemented")
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

        call.respond((listOf(discuss) + replies).asDetail().asRestResponse())
    }
}


fun Route.getDiscussList() {
    get("/discuss-list") {
        val allDiscusses = transaction {
            Discuss.all().toList()
        }

        call.respond(allDiscusses.asDetail().asRestResponse())
    }
}

fun Route.deleteDiscuss() {
    TODO("Not yet implemented")
}

fun Route.createDiscuss() {
    post<ForumRoute.Reply.Object>("/create-discuss") {
        val currentUser = call.authentication.principal<User>()
        if (currentUser == null){
            call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
            return@post
        }
        val user = transaction { User.findById(it.posterId) }
        if (user == null){
            call.respond(failRestResponseDefault(-2, "Wrong Argument: UserId"))
            return@post
        }
        if(user.id.value != it.posterId){
            call.respond(failRestResponseDefault(-3, "Wrong Authentic: Access Denied"))
            return@post
        }
        val discuss = Discuss.new {
            title = it.title
            content = it.content
            poster = currentUser
            belongsToId = id
            createTime = Instant.ofEpochSecond(it.time)
            likes = 0
        }
        call.respond(discuss.asDetail().asRestResponse("Operator Success: Create discuss with id: ${discuss.id}$"))
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
        if (likeRecord.empty()) {
            LikeTable.insert { record ->
                record[LikeTable.likerId] = id
                record[LikeTable.discussId] = discussId
            }
            discuss.likes.plus(1)
            call.respond(discuss.likes.asRestResponse("Operator Success: Like"))
        } else {
            LikeTable.deleteWhere { (LikeTable.likerId eq id) and (LikeTable.discussId eq discussId) }
            discuss.likes.plus(-1)
            call.respond(discuss.likes.asRestResponse("Operator Success: Unlike"))
        }
    }
}

class ForumRoute {
    class Reply(val parent: ForumRoute = ForumRoute()) {
        @Serializable
        data class Object(
            val title: String,
            val content: String,
            val posterId: Long,
            val time: Long,
        )
    }
}