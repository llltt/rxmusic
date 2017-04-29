package suhockii.rxmusic.business.audio

import io.reactivex.Completable
import io.reactivex.Single
import suhockii.rxmusic.data.net.models.Audio
import suhockii.rxmusic.data.net.models.AudioResponse
import suhockii.rxmusic.data.net.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {

    fun getAudio(ownerId: String,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>

    fun startPlaying(audio: Audio): Completable
}