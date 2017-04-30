package rx.music.business.auth

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.data.net.models.Credentials
import rx.music.data.repositories.auth.AuthRepository

/** Created by Maksim Sukhotski on 3/27/2017.*/
class AuthInteractorImpl(private val repository: AuthRepository) : AuthInteractor {

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