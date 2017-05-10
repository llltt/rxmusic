package rx.music.business.auth

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.dagger.Dagger
import rx.music.data.auth.AuthRepo
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.Credentials
import javax.inject.Inject

/** Created by Maksim Sukhotski on 3/27/2017.*/
class AuthInteractorImpl : AuthInteractor {

    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var authRepo: AuthRepo

    init {
        Dagger.instance.authComponent?.inject(this)
    }

    override fun getCredentials(username: String, password: String, captchaSid: String?,
                                captchaKey: String?, code: Int?): Single<Credentials> {
        return authRepo.getCredentials(username, password, captchaSid, captchaKey, code)
                .doOnSuccess { preferencesRepo.credentials = it }
    }

    override fun validatePhone(sid: String): Completable {
        return authRepo.validatePhone(sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}