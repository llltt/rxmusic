package rx.music.business.audio

import Items
import Response
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.net.models.audio.Audio

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {
    fun getAudio(ownerId: Long?, count: Int, offset: Int):
            Observable<Response<Items<MutableList<Audio>>>>
    fun handleAudio(audio: Audio): Single<Audio>
}