package rx.music.data.audio

import io.reactivex.Observable
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioRepo {
    fun getAudio(ownerId: Long?, count: Int, offset: Int): Observable<Base<AudioResponse>>

    val isAuthorized: Boolean
}