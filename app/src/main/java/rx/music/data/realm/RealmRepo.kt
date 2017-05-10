package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import rx.music.net.models.CustomSearch

/** Created by Maksim Sukhotski on 5/9/2017. */

interface RealmRepo {
    fun putAudio(item: Base<AudioResponse>): Completable
    fun getAudio(ownerId: Long?): Observable<Base<AudioResponse>>
    fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio>
}