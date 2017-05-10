package rx.music.business.audio

import io.reactivex.Completable
import io.reactivex.Observable
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {
    fun getAudio(ownerId: Long?, count: Int, offset: Int): Observable<Base<AudioResponse>>
    fun handleAudio(audio: Audio): Completable
    val isAuthorized: Boolean
}