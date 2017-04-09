package suhockii.rxmusic.business.auth

import io.reactivex.Completable
import io.reactivex.Single
import suhockii.rxmusic.data.repositories.auth.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/
interface AuthInteractor {

    fun getCredentials(username: String,
                       password: String,
                       captchaSid: String?,
                       captchaKey: String?,
                       code: String?): Single<Credentials>

    fun validatePhone(sid: String): Completable
}