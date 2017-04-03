package suhockii.rxmusic.data.repositories.auth

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.CLIENT_ID
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.CLIENT_SECRET
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.GRANT_TYPE
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.LANG
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.LIBVERIFY_SUPPORT
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.SCOPE
import suhockii.rxmusic.data.repositories.auth.AuthApi.Companion.TWO_FA_SUPPORTED
import suhockii.rxmusic.data.repositories.auth.models.Auth
import java.io.IOException

/** Created by Maksim Sukhotski on 3/27/2017.*/

class AuthRepositoryImpl : AuthRepository {

    private val retrofit: Retrofit

    lateinit private var token: AuthApi private set

    init {
        retrofit = createRetrofit()
    }

    private fun createRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(AuthApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build()
        token = retrofit.create(AuthApi::class.java)
        return retrofit
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(createHttpLoggingInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    @NotNull
    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    override fun login(username: String, password: String): Single<Auth> {
        return token.token(
                SCOPE,
                CLIENT_ID,
                CLIENT_SECRET,
                TWO_FA_SUPPORTED,
                LANG,
                GRANT_TYPE,
                LIBVERIFY_SUPPORT,
                username,
                password)
    }

    internal class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
//            if (PreferencesRepository.TOKEN.isNotEmpty()) {
//                request = request.newBuilder()
//                        .addHeader("Authorization", "Bearer ${PreferencesRepository.TOKEN}")
//                        .build()
//            }
            val response = chain.proceed(request)
            return response
        }
    }
}