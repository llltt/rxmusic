package rx.music.data.repositories.auth

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.music.BuildConfig.CLIENT_ID
import rx.music.BuildConfig.CLIENT_SECRET
import rx.music.data.net.AuthApi
import rx.music.data.net.BaseFields.Companion.GRANT_TYPE
import rx.music.data.net.BaseFields.Companion.HTTPS
import rx.music.data.net.BaseFields.Companion.LANG
import rx.music.data.net.BaseFields.Companion.LIBVERIFY_SUPPORT
import rx.music.data.net.BaseFields.Companion.SCOPE
import rx.music.data.net.BaseFields.Companion.TWO_FA_SUPPORTED
import rx.music.data.net.BaseFields.Companion.V
import rx.music.data.net.RetrofitObject
import rx.music.data.net.models.Credentials

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