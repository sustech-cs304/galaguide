package galaGuide.data.user

import galaGuide.data.asRestResponse
import galaGuide.table.user.User
import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserDetail(
    val name: String,
    val email: String,
    val guiro: Long,
    val emailVerified: Boolean,
)

fun User.asPrivateDetail() = PrivateUserDetail(
    name,
    email,
    guiro,
    emailVerified,
)

fun User.asPrivateResponse() = asPrivateDetail().asRestResponse()

data class PublicUserDetail(
    val name: String,
)

fun User.asPublicDetail() = PublicUserDetail(
    name,
)

fun User.asPublicResponse() = asPublicDetail().asRestResponse()