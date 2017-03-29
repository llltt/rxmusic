package suhockii.rxmusic.data.repositories.auth.models

/** Created by Maksim Sukhotski on 3/27/2017.*/
class BaseResponse<T> {
    val status: String? = null
    val data: T? = null
}