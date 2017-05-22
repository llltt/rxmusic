package rx.music.net.interceptors

import me.extensions.toMd5
import me.extensions.toRaw
import okhttp3.*
import rx.music.App
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.BaseFields.Companion.V
import rx.music.net.models.auth.Credentials
import javax.inject.Inject


/** Created by Maksim Sukhotski on 5/15/2017. */
class SecretsInterceptor : Interceptor {
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        App.appComponent.inject(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var body = request.body()
        if (body?.contentType()?.subtype()?.contains("form") ?: false)
            body = changeRequestBody(request, preferencesRepo.credentials)
        request = request.newBuilder().post(body).build()
        return chain.proceed(request)
    }

    private fun changeRequestBody(request: Request, credentials: Credentials):
            RequestBody = with(credentials) {
        val newBody = FormBody.Builder()
                .add("v", V.toString())
                .add("access_token", token)
                .add("sig", ("${request.url().encodedPath()}?${request.toRaw()}" +
                        "&v=$V&access_token=$token$secret").toMd5()).build()
        return RequestBody.create(request.body()?.contentType(), request.toRaw() +
                (if (request.toRaw().isNotEmpty()) "&" else "") + newBody.toRaw())
    }
}



