package suhockii.rxmusic.data.api.vk

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.CLIENT_ID
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.CLIENT_SECRET
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.GRANT_TYPE
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.LANG
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.LIBVERIFY_SUPPORT
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.SCOPE
import suhockii.rxmusic.data.api.vk.AuthApi.Companion.TWO_FA_SUPPORTED

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
                .client(OkHttpClient())
                .build()
        token = retrofit.create(AuthApi::class.java)
        return retrofit
    }

    override fun login(username: String, password: String): Single<String> {
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
}