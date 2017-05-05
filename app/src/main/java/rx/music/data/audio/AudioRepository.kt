package rx.music.data.audio

import io.reactivex.Single
import rx.music.network.models.AudioResponse
import rx.music.network.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepository {
    fun getAudio(ownerId: String?,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>

    val isNotAuthorized: Boolean
}