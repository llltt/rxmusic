package suhockii.rxmusic.data.api.vk.errors

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/** Created by Maksim Sukhotski on 3/27/2017.*/

class RetrofitException private constructor(message: String, val response: Response<*>?, val kind: RetrofitException.Kind, exception: Throwable) : RuntimeException(message, exception) {

    val serverError: ErrorsResponse?
        get() {
            if (response == null || response.errorBody() == null) {
                return null
            }
            val gson = Gson()
            var serverError: ErrorsResponse? = null

            try {
                serverError = gson.fromJson<ErrorsResponse>(response.errorBody().string(), ErrorsResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return serverError
        }

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    companion object {

        internal fun createException(throwable: Throwable): RetrofitException {

            if (throwable is HttpException) {
                val response = throwable.response()
                return RetrofitException(response.message(), response, Kind.HTTP, throwable)
            }

            if (throwable is IOException) {
                return RetrofitException(throwable.message!!, null, Kind.NETWORK, throwable)
            }

            return RetrofitException(throwable.message!!, null, Kind.UNEXPECTED, throwable)
        }
    }
}