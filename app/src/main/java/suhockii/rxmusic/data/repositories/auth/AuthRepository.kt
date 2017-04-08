package suhockii.rxmusic.data.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single
import suhockii.rxmusic.data.repositories.auth.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/

interface AuthRepository {
    fun login(username: String,
              password: String,
              captchaSid: String?,
              captchaKey: String?,
              code: String?): Single<Credentials>

    fun validatePhone(sid: String): Completable
}
