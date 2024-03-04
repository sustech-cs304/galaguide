package galaGuide.data

import kotlinx.serialization.Serializable

@Serializable
data class RestResponse<out T>(val code: Int, val message: String, val data: T? = null) {
    companion object {
        fun <T> success(data: T): RestResponse<T> {
            return RestResponse(0, "success", data)
        }

        fun <T> error(code: Int, message: String): RestResponse<T> {
            return RestResponse(code, message)
        }
    }
}

fun <T> T.asRestResponse(): RestResponse<T> {
    return RestResponse.success(this)
}

fun <T> failRestResponse(code: Int, message: String): RestResponse<T> {
    return RestResponse.error(code, message)
}
