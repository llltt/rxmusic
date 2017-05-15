package rx.music.net.interceptors

import me.extensions.toMd5
import me.extensions.toRaw
import okhttp3.*
import rx.music.net.BaseFields.Companion.V
import rx.music.net.models.Credentials


/** Created by Maksim Sukhotski on 5/15/2017. */
class VkInterceptor(private val credentials: Credentials) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var body = request.body()
        if (body.contentType().subtype().contains("form"))
            body = changeRequestBody(request, credentials)
        request = request.newBuilder().post(body).build()
        return chain.proceed(request)
    }

    private fun changeRequestBody(request: Request, credentials: Credentials): RequestBody =
            with(credentials) {
                val newBody = FormBody.Builder()
                        .add("v", V.toString())
                        .add("access_token", access_token)
                        .add("sig", ("${request.url().encodedPath()}?${request.toRaw()}" +
                                "&v=$V&access_token=$access_token$secret").toMd5()).build()
                return RequestBody.create(request.body().contentType(), request.toRaw() +
                        (if (request.toRaw().isNotEmpty()) "&" else "") + newBody.toRaw())
            }
}



