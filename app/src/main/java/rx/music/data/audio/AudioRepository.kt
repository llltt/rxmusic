package rx.music.data.audio

import io.reactivex.Single
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepository {
    fun getAudio(ownerId: Long?, count: Int, offset: Int): Single<Base<AudioResponse>>

    val isAuthorized: Boolean
}