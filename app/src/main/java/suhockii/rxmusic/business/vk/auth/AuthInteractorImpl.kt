package suhockii.rxmusic.business.vk.auth

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.auth.AuthRepositoryImpl

/** Created by Maksim Sukhotski on 3/27/2017.*/
class AuthInteractorImpl : AuthInteractor {

    private val repository: AuthRepository = AuthRepositoryImpl()

    override fun login(username: String, password: String): Single<String> {
        return repository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}