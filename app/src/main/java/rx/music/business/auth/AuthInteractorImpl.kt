package rx.music.business.auth

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.App
import rx.music.data.auth.AuthRepository
import rx.music.data.preferences.PreferencesRepository
import rx.music.net.models.Credentials
import javax.inject.Inject

/** Created by Maksim Sukhotski on 3/27/2017.*/
class AuthInteractorImpl : AuthInteractor {

    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var authRepository: AuthRepository

    init {
        App.instance.authComponent?.inject(this)
    }

    override fun getCredentials(username: String, password: String, captchaSid: String?,
                                captchaKey: String?, code: String?): Single<Credentials> {
        return authRepository.getCredentials(username, password, captchaSid, captchaKey, code)
                .doOnSuccess { preferencesRepository.credentials = it }
    }

    override fun validatePhone(sid: String): Completable {
        return authRepository.validatePhone(sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}