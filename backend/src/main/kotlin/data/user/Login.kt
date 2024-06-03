package galaGuide.data.user

import galaGuide.table.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(val name: String = "", val password: String = "", val email: String = "")

@Serializable
data class LoginRequest(val nameOrEmail: String = "", val password: String = "")

@Serializable
data class LoginData(
    val token: String,
    val userName: String,
    val userRole: UserRole,
)