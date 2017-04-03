package suhockii.rxmusic.data.repositories.auth

import io.reactivex.Single
import suhockii.rxmusic.data.repositories.auth.models.Auth

/** Created by Maksim Sukhotski on 3/27/2017.*/

interface AuthRepository {
    fun login(username: String,
              password: String): Single<Auth>
}
