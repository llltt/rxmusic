package suhockii.rxmusic.business.audio

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import suhockii.rxmusic.data.net.models.BaseResponse
import suhockii.rxmusic.data.repositories.audio.AudioRepository
import suhockii.rxmusic.data.repositories.audio.models.AudioResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl(private val repository: AudioRepository) : AudioInteractor {

    override fun getAudio(ownerId: String,
                          count: String,
                          offset: String): Single<BaseResponse<AudioResponse>> {
        return repository.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}