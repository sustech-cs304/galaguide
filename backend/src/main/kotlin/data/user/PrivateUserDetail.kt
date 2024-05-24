package galaGuide.data.user

import galaGuide.data.asRestResponse
import galaGuide.table.user.User
import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserDetail(
    val id: Long,
    val name: String,
    val email: String,
    val guiro: Long,
    val emailVerified: Boolean,
)

fun User.asPrivateDetail() = PrivateUserDetail(
    id.value,
    name,
    email,
    guiro,
    emailVerified,
)

fun User.asPrivateResponse() = asPrivateDetail().asRestResponse()

data class PublicUserDetail(
    val id: Long,
    val name: String,
)

fun User.asPublicDetail() = PublicUserDetail(
    id.value,
    name,
)

fun User.asPublicResponse() = asPublicDetail().asRestResponse()