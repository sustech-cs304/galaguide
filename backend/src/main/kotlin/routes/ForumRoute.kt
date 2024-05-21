package galaGuide.routes


import galaGuide.data.asDetail
import galaGuide.data.failRestResponseDefault
import galaGuide.table.forum.*
import galaGuide.table.user.User
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime

fun Route.forum() = authenticate("user") {
    route("/discuss") {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(DiscussTable, TagTable, DiscussTagTable, LikeTable)
        }
        createDiscuss()
        deleteDiscuss()
        getDiscussList()
        getReplyList()
        updateDiscussAccessHistory()
        uploadDiscussReply()
        getSimilarDiscuss()
        updateDiscussLikes()
    }
}

fun Route.getSimilarDiscuss() {
    get("/{discussId}/related") {
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing discussId")
            return@get
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(HttpStatusCode.NotFound, "Missing discuss")
            return@get
        }

        val discussTags = transaction { discuss.tags.map { it.name }.toSet() } // 获取当前讨论的所有标签
        if (discussTags.isEmpty()) {
            call.respond(HttpStatusCode.OK, emptyList<Discuss>()) // 如果当前讨论没有标签，直接返回空列表
            return@get
        }

        val allDiscussWithTags = transaction {
            Discuss.all()
                .filter { it.id.value != discussId && it.id.value == it.belongsTo.id.value && it.tags.any { tag -> tag.name in discussTags } } // 获取所有和当前讨论具有相同标签的其他讨论
        }

        val sortedDiscusses =
            allDiscussWithTags.groupBy { it.tags.count { tag -> tag.name in discussTags } } // 按照和当前讨论共有标签的数量进行分组
                .toList().sortedByDescending { (count, _) -> count }
                .take(10) // 取共有标签数量前十的讨论
                .flatMap { (_, discusses) -> discusses }

        call.respond(HttpStatusCode.OK, sortedDiscusses)
    }
}


fun Route.uploadDiscussReply() {
    post("/{discussId}/upload-reply") {
        val discussId = call.parameters["discussId"]?.toLong()
        if (discussId == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing discussId")
            return@post
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-1, "Missing discuss"))
            return@post
        }
        val replyObject = call.receive<ForumRoute.Reply.Object>()
        val currentUser = call.authentication.principal<User>()
        if (currentUser == null) {
            call.respond(failRestResponseDefault(-2, "Not logged in"))
            return@post
        }
        if (currentUser.id.value != replyObject.posterId) {
            call.respond(failRestResponseDefault(-3, "Access Denied"))
            return@post
        }
        val title = replyObject.title
        val posterId = replyObject.posterId
        val content = replyObject.content
        val createTime = replyObject.time
        val replyId = DiscussTable.insertAndGetId { reply ->
            reply[DiscussTable.likes] = 0
            reply[DiscussTable.title] = title
            reply[DiscussTable.content] = content
            reply[DiscussTable.createTime] = Instant.ofEpochSecond(createTime)
            reply[DiscussTable.posterId] = posterId
            reply[DiscussTable.belongsToId] = discuss.id
        }
        kotlin.runCatching { call.respond(HttpStatusCode.Created, "Reply created with ID: $replyId") }
            .getOrElse {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to create reply: ${it.message}"
                )
            }
    }
}

fun Route.updateDiscussAccessHistory() {
    TODO("Not yet implemented")
}

fun Route.getReplyList() {
    get("/{discuss_id}") {
        val discussId = call.parameters["discuss_id"]?.toLong()
        if (discussId == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing discussId")
            return@get
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(HttpStatusCode.NotFound, "Missing discuss")
            return@get
        }

        // 获取主贴的信息
        val mainPostJson = mapOf(
            "id" to discuss.id.value,
            "title" to discuss.title,
            "content" to discuss.content,
            "posterName" to discuss.poster.name,
            "createTime" to discuss.createTime.toString(),
            "likes" to discuss.likes,
            "tags" to discuss.tags.map { it.name }
            // 添加其他字段和属性
        )

        // 获取该帖子的所有回复并按时间排序
        val replies = transaction {
            Discuss.find { DiscussTable.belongsToId eq discussId }.toList().sortedBy { it.createTime }
        }

        // 构造所有回复的 JSON 数组
        val replyJsonArray = replies.map { reply ->
            mapOf(
                "id" to reply.id.value,
                "content" to reply.content,
                "posterName" to reply.poster.name,
                "createTime" to reply.createTime.toString(),
                "title" to "", // 回复没有标题，设置为默认值
                "likes" to reply.likes,
                "tags" to emptyList<String>() // 回复没有标签，设置为空列表
            )
        }

        // 构造包含主贴和所有回复的 JSON 响应
        val discussAndRepliesJson = listOf(mainPostJson) + replyJsonArray

        call.respond(discussAndRepliesJson)
    }
}


fun Route.getDiscussList() {
    get("/discuss-list") {
        val allDiscusses = transaction {
            Discuss.all().toList()
        }

        val discussesJson = allDiscusses.map { discuss -> discuss.asDetail() }

        call.respond(discussesJson)
    }
}

fun Route.deleteDiscuss() {
    TODO("Not yet implemented")
}

fun Route.createDiscuss() {
    post("/create-discuss") {

    }
}

fun Route.updateDiscussLikes() {
    post("/{discuss_id}/like/{id}") {
        val discussId = call.parameters["discuss_id"]?.toLong()
        if (discussId == null) {
            call.respond(failRestResponseDefault(-2, "Missing argument"))
            return@post
        }
        val discuss = transaction { Discuss.findById(discussId) }
        if (discuss == null) {
            call.respond(failRestResponseDefault(-1, "Missing discuss"))
            return@post
        }
        val id = call.parameters["id"]?.toLong()
        if (id == null) {
            call.respond(failRestResponseDefault(-2, "Missing argument"))
            return@post
        }
        val user = transaction { User.findById(id) }
        if (user == null) {
            call.respond(failRestResponseDefault(-3, "Missing user"))
            return@post
        }
        val likeRecord =
            transaction { LikeTable.select { (LikeTable.likerId eq id) and (LikeTable.discussId eq discussId) } }
        if (likeRecord.empty()){
            LikeTable.insert { record->
                record[LikeTable.likerId] = id
                record[LikeTable.discussId] = discussId
            }
        }
    }
}

@Resource("/reserve")
class ForumRoute {
    @Resource("replyRequest")
    class Reply(val parent: ForumRoute = ForumRoute()) {
        @Serializable
        data class Object(
            val title: String,
            val content: String,
            val posterId: Long,
            val time: Long
        )
    }
}