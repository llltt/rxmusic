package suhockii.rxmusic.data.repositories.auth

import io.reactivex.Single

/** Created by Maksim Sukhotski on 3/27/2017.*/

interface AuthRepository {
    fun login(username: String,
              password: String): Single<String>
}
