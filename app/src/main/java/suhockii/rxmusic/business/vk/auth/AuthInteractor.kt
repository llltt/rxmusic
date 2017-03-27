package suhockii.rxmusic.business.vk.auth

import io.reactivex.Single

/** Created by Maksim Sukhotski on 3/27/2017.*/
interface AuthInteractor {
    fun login(username: String, password: String): Single<Any>
}