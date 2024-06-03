package galaGuide.data

import kotlinx.serialization.Serializable

@Serializable
data class RestResponse<out T>(val code: Int, val message: String, val data: T? = null) {
    companion object {
        fun <T> success(data: T, message: String = "success"): RestResponse<T> {
            return RestResponse(0, message, data)
        }

        fun <T> error(code: Int, message: String): RestResponse<T?> {
            return RestResponse(code, message)
        }
    }
}

fun <T> T.asRestResponse(message: String = "success"): RestResponse<T> {
    return RestResponse.success(this, message)
}

fun emptyRestResponse(message: String = "success") = Unit.asRestResponse(message)

fun <T> failRestResponse(code: Int, message: String): RestResponse<T?> {
    return RestResponse.error(code, message)
}

fun failRestResponseDefault(code: Int, message: String): RestResponse<Unit?> {
    return RestResponse.error(code, message)
}
