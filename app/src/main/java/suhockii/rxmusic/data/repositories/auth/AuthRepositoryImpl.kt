package suhockii.rxmusic.data.repositories.auth

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import suhockii.rxmusic.data.net.AuthApi
import suhockii.rxmusic.data.net.ConstantFields.Companion.CLIENT_ID
import suhockii.rxmusic.data.net.ConstantFields.Companion.CLIENT_SECRET
import suhockii.rxmusic.data.net.ConstantFields.Companion.GRANT_TYPE
import suhockii.rxmusic.data.net.ConstantFields.Companion.HTTPS
import suhockii.rxmusic.data.net.ConstantFields.Companion.LANG
import suhockii.rxmusic.data.net.ConstantFields.Companion.LIBVERIFY_SUPPORT
import suhockii.rxmusic.data.net.ConstantFields.Companion.SCOPE
import suhockii.rxmusic.data.net.ConstantFields.Companion.TWO_FA_SUPPORTED
import suhockii.rxmusic.data.net.ConstantFields.Companion.V
import suhockii.rxmusic.data.net.RetrofitObject
import suhockii.rxmusic.data.repositories.auth.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/

class AuthRepositoryImpl : AuthRepository {

    internal val api = RetrofitObject.build(AuthApi::class.java)

    companion object {
        const val OAUTH_URL = "https://oauth.vk.com/"
    }

    private var authApi: AuthApi private set

    init {
        authApi = Retrofit.Builder()
                .baseUrl(OAUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
                .build()
                .create(AuthApi::class.java)
    }

    override fun getCredentials(username: String,
                                password: String,
                                captchaSid: String?,
                                captchaKey: String?,
                                code: String?): Single<Credentials> {
        return authApi.getCredentials(
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
        return api.validatePhone(V, LANG, HTTPS, sid, CLIENT_ID)
    }
}