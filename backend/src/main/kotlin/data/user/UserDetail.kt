package galaGuide.data.user

import galaGuide.data.asRestResponse
import galaGuide.table.user.User
import galaGuide.table.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserDetail(
    val id: Long,
    val name: String,
    val email: String,
    val guiro: Long,
    val emailVerified: Boolean,
    val avatarId: String?,
    val backgroundId: String?,
    val intro: String,
    val role: UserRole,
)

fun User.asPrivateDetail() = PrivateUserDetail(
    id.value,
    name,
    email,
    guiro,
    emailVerified,
    avatar?.id?.value?.toString(),
    background?.id?.value?.toString(),
    intro,
    role,
)

fun User.asPrivateResponse() = asPrivateDetail().asRestResponse()

@Serializable
data class PublicUserDetail(
    val id: Long,
    val name: String,
    val avatarId: String?,
    val backgroundId: String?,
    val intro: String,
)

fun User.asPublicDetail() = PublicUserDetail(
    id.value,
    name,
    avatar?.id?.value?.toString(),
    background?.id?.value?.toString(),
    intro,
)

fun User.asPublicResponse() = asPublicDetail().asRestResponse()