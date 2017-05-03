package rx.music.data.repositories.audio

import io.reactivex.Single
import rx.music.data.network.models.AudioResponse
import rx.music.data.network.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepository {
    fun getAudio(ownerId: String?,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>

    val isNotAuthorized: Boolean
}