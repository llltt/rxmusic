package rx.music.data.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single
import rx.music.App
import rx.music.BuildConfig.VK_CLIENT_ID
import rx.music.BuildConfig.VK_CLIENT_SECRET
import rx.music.data.network.BaseFields.Companion.GRANT_TYPE
import rx.music.data.network.BaseFields.Companion.HTTPS
import rx.music.data.network.BaseFields.Companion.LANG
import rx.music.data.network.BaseFields.Companion.LIBVERIFY_SUPPORT
import rx.music.data.network.BaseFields.Companion.SCOPE
import rx.music.data.network.BaseFields.Companion.TWO_FA_SUPPORTED
import rx.music.data.network.BaseFields.Companion.V
import rx.music.data.network.BaseFields.Companion.VK_VALIDATION_API
import rx.music.data.network.apis.AuthApi
import rx.music.data.network.models.Credentials
import javax.inject.Inject

/** Created by Maksim Sukhotski on 3/27/2017.*/

class AuthRepositoryImpl : AuthRepository {
    @Inject lateinit var authApi: AuthApi

    init {
        App.instance.authComponent?.inject(this)
    }

    override fun getCredentials(username: String, password: String, captchaSid: String?,
                                captchaKey: String?, code: String?): Single<Credentials> {
        return authApi.getCredentials(VK_VALIDATION_API, SCOPE, VK_CLIENT_ID, VK_CLIENT_SECRET,
                TWO_FA_SUPPORTED, LANG, GRANT_TYPE, LIBVERIFY_SUPPORT,
                username, password, captchaSid, captchaKey, code)
    }

    override fun validatePhone(sid: String): Completable {
        return authApi.validatePhone(V, LANG, HTTPS, sid, VK_CLIENT_ID)
    }
}