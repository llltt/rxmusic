package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Single
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base

/** Created by Maksim Sukhotski on 5/9/2017. */
interface RealmRepository {
    fun putAudio(items: List<Audio>): Completable

    fun getAudio(ownerId: Long?, count: Int, offset: Int): Single<Base<AudioResponse>>
}