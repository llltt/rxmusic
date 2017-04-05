package suhockii.rxmusic.data.repositories.auth

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import suhockii.rxmusic.data.repositories.auth.models.Auth

/** Created by Maksim Sukhotski on 3/27/2017.*/

class AuthRepositoryImpl : AuthRepository {

    companion object {
        const val OAUTH_URL = "https://oauth.vk.com/"
        const val API_URL = "https://api.vk.com/method/"
        const val V = "5.63"
        const val SCOPE = "nohttps,all"
        const val CLIENT_ID = "2274003"
        const val CLIENT_SECRET = "hHbZxrka2uZ6jB1inYsH"
        const val TWO_FA_SUPPORTED = "1"
        const val LANG = "ru"
        const val GRANT_TYPE = "password"
        const val LIBVERIFY_SUPPORT = "1"
        const val HTTPS = "1"
    }

    private var api: AuthApi private set

    init {
        api = Retrofit.Builder()
                .baseUrl(OAUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build()
                .create(AuthApi::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//                .addInterceptor(HeaderInterceptor())
                .addInterceptor(createHttpLoggingInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    fun changeApiBaseUrl(url: String) {
        api = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build()
                .create(AuthApi::class.java)
    }

    @NotNull
    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    override fun login(username: String, password: String, captchaSid: String?, captchaKey: String?, code: String?): Single<Auth> {
        changeApiBaseUrl(OAUTH_URL)
        return api.token(
                SCOPE,
                CLIENT_ID,
                CLIENT_SECRET,
                TWO_FA_SUPPORTED,
                LANG,
                GRANT_TYPE,
                LIBVERIFY_SUPPORT,
                username,
                password,
                captchaSid,
                captchaKey,
                code)
    }

    override fun validatePhone(sid: String): Completable {
        changeApiBaseUrl(API_URL)
        return api.validatePhone(V, LANG, HTTPS, sid, CLIENT_ID)
    }
//    internal class HeaderInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            var request = chain.request()
////            if (PreferencesRepository.TOKEN.isNotEmpty()) {
////                request = request.newBuilder()
////                        .addHeader("Authorization", "Bearer ${PreferencesRepository.TOKEN}")
////                        .build()
////            }
//            val response = chain.proceed(request)
//            return response
//        }
//    }
}