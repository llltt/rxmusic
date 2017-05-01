package rx.music.business.audio

import io.reactivex.Completable
import io.reactivex.Single
import rx.music.data.net.models.Audio
import rx.music.data.net.models.AudioResponse
import rx.music.data.net.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {

    fun getAudio(ownerId: String?,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>

    fun handleAudio(audio: Audio): Completable
    val isNotAuthorized: Boolean
}