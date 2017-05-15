package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.*
import javax.inject.Inject
import javax.inject.Provider


/** Created by Maksim Sukhotski on 5/9/2017. */

class RealmRepoImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun putAudio(audio: Response<AudioResponse>, offset: Int): Completable =
            with(realmProvider.get()) {
                Completable.fromCallable {
                    executeTransactionAsync {
                        val items = audio.response?.items
                        items?.forEachIndexed { index, audio -> audio.pos = offset + index }
                        it.insertOrUpdate(items)
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun getAudio(ownerId: Long?): Observable<Response<AudioResponse>> =
            with(realmProvider.get()) {
                Observable.fromCallable {
                    where(Audio::class.java).findAll()?.toAudioResponse() ?: Response<AudioResponse>()
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

    override fun getUsers(userIds: String?): Single<Response<List<User>>> =
            with(Pair(realmProvider.get(), preferencesRepo.credentials)) {
                Single.fromCallable {
                    first.where(User::class.java).equalTo(User::id.name, second.user_id)
                            .findAll()?.toUsersResponse() ?: Response<List<User>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun putUsers(audio: Response<List<User>>): Completable =
            with(realmProvider.get()) {
                Completable.fromAction {
                    executeTransactionAsync { it.insertOrUpdate(audio.response) }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
}

@Suppress("UNCHECKED_CAST")
private fun <E> MutableList<E>.toAudioResponse(): Response<AudioResponse> =
        Response(response = AudioResponse(this.size, this as MutableList<Audio>))

@Suppress("UNCHECKED_CAST")
private fun <E> MutableList<E>.toUsersResponse(): Response<List<E>>? =
        Response(response = this)
