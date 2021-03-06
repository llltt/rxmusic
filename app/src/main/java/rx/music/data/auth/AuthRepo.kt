package rx.music.data.auth

import io.reactivex.Completable
import io.reactivex.Single
import rx.music.net.models.auth.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/
interface AuthRepo {
    fun getCredentials(username: String,
                       password: String,
                       captchaSid: String?,
                       captchaKey: String?,
                       code: String?): Single<Credentials>

    fun validatePhone(sid: String): Completable
}
