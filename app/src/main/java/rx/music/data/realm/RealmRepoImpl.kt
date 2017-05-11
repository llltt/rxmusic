package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import rx.music.net.models.CustomSearch
import javax.inject.Inject
import javax.inject.Provider


/** Created by Maksim Sukhotski on 5/9/2017. */

class RealmRepoImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepo {
    override fun putAudio(item: Base<AudioResponse>): Completable =
            Completable.fromAction {
                realmProvider.get().executeTransaction { it.insertOrUpdate(item.response?.items) }
            }

    override fun getAudio(ownerId: Long?): Observable<Base<AudioResponse>> =
            Observable.fromCallable {
                realmProvider.get()?.where(Audio::class.java)?.findAll()?.toBase() ?: Base<AudioResponse>()
            }

    override fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio> =
            Single.fromCallable {
                val t = realmProvider.get().where(Audio::class.java).equalTo(Audio::id.name, audio.id).findFirst()
                realmProvider.get().executeTransaction { t.pic = customSearch.items[0].link }
                return@fromCallable realmProvider.get().copyFromRealm(t)
            }
}

@Suppress("UNCHECKED_CAST")
private fun <E> MutableList<E>.toBase(): Base<AudioResponse> =
        Base(response = AudioResponse(this.size, this as MutableList<Audio>))
