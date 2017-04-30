package rx.music.data.repositories.audio

import io.reactivex.Single
import rx.music.data.net.models.AudioResponse
import rx.music.data.net.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepository {

    fun getAudio(ownerId: String,
                 count: String,
                 offset: String): Single<BaseResponse<AudioResponse>>
}