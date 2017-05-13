package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import rx.music.net.models.CustomSearch
import javax.inject.Inject
import javax.inject.Provider


/** Created by Maksim Sukhotski on 5/9/2017. */

class RealmRepoImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepo {
    override fun putAudio(audio: Base<AudioResponse>, offset: Int): Completable =
            with(realmProvider.get()) {
                Completable.fromCallable {
                    executeTransactionAsync {
                        val items = audio.response?.items
                        items?.forEachIndexed { index, audio -> audio.pos = offset + index }
                        it.insertOrUpdate(items)
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun getAudio(ownerId: Long?): Observable<Base<AudioResponse>> =
            with(realmProvider.get()) {
                Observable.fromCallable {
                    where(Audio::class.java)?.findAll()?.toBase() ?: Base<AudioResponse>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio> =
            with(realmProvider.get()) {
                Single.fromCallable {
                    val t = where(Audio::class.java).equalTo(Audio::id.name, audio.id).findFirst()
                    executeTransaction { t.pic = customSearch.items?.get(0)?.link ?: "" }
                    return@fromCallable copyFromRealm(t)
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
}

@Suppress("UNCHECKED_CAST")
private fun <E> MutableList<E>.toBase(): Base<AudioResponse> =
        Base(response = AudioResponse(this.size, this as MutableList<Audio>))
