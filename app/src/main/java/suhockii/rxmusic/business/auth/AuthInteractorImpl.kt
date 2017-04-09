package suhockii.rxmusic.business.auth

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import suhockii.rxmusic.App
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.auth.models.Credentials
import javax.inject.Inject

/** Created by Maksim Sukhotski on 3/27/2017.*/
class AuthInteractorImpl : AuthInteractor {

    @Inject lateinit var repository: AuthRepository

    init {
        App.appComponent.inject(this)
    }

    override fun getCredentials(username: String,
                                password: String,
                                captchaSid: String?,
                                captchaKey: String?,
                                code: String?): Single<Credentials> {
        return repository.getCredentials(username, password, captchaSid, captchaKey, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun validatePhone(sid: String): Completable {
        return repository.validatePhone(sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}