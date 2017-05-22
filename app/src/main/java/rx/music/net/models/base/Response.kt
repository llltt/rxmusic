package rx.music.net.models.base

import com.google.gson.annotations.SerializedName

/** Created by Maksim Sukhotski on 4/9/2017. */
class Response<out T>(val response: T? = null,
                      val error: Error? = null,
                      @SerializedName("execute_errors") val executeErrors: MutableList<Error>? = null)

class Error(val error_code: Int, val error_msg: String, val method: String?)