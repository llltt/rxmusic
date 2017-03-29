package suhockii.rxmusic.data.repositories.auth.errors

/** Created by Maksim Sukhotski on 3/27/2017.*/
interface OnErrorConsumer {
    fun onError(retrofitException: RetrofitException)
}