package suhockii.rxmusic.data.repositories.auth

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import suhockii.rxmusic.data.repositories.auth.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/

class AuthRepositoryImpl : AuthRepository {

//    internal val api = Retrofit.build(ApiProfile::class.java)

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
                .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
                .build()
                .create(AuthApi::class.java)
    }

    override fun login(username: String, password: String, captchaSid: String?, captchaKey: String?, code: String?): Single<Credentials> {
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
        val validatePhoneApi = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
                .build()
                .create(AuthApi::class.java)
        return validatePhoneApi.validatePhone(V,
                LANG,
                HTTPS,
                sid,
                CLIENT_ID)
    }
}