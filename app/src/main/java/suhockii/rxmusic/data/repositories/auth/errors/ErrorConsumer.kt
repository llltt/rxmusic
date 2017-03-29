package suhockii.rxmusic.data.repositories.auth.errors

import io.reactivex.functions.Consumer

/** Created by Maksim Sukhotski on 3/27/2017.*/
class ErrorConsumer(private val onErrorConsumer: OnErrorConsumer) : Consumer<Throwable> {

    @Throws(Exception::class)
    override fun accept(throwable: Throwable) {
        onErrorConsumer.onError(RetrofitException.createException(throwable))
    }
}