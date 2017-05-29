package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import me.extensions.*
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.base.Items
import rx.music.net.models.base.Response
import rx.music.net.models.google.CustomSearch
import rx.music.net.models.vk.*
import javax.inject.Inject
import javax.inject.Provider


/** Created by Maksim Sukhotski on 5/9/2017. */

class RealmRepoImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun putAudio(ownerId: Long?, audioResponse: Response<Items<MutableList<Audio>>>,
                          count: Int, offset: Int): Completable =
            with(preferencesRepo.credentials) {
                Completable.fromCallable {
                    realmProvider.get().executeTransactionAsync {
                        it.insertOrUpdate(audioResponse.response?.items)
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun getAudio(ownerId: Long?): Observable<Response<Items<MutableList<Audio>>>> =
            with(realmProvider.get()) {
                Observable.fromCallable {
                    where(Audio::class.java)
                            .findAll()?.toAudioResponse() ?: Response<Items<MutableList<Audio>>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun getUsers(userIds: String?): Single<Response<List<User>>> =
            with(preferencesRepo.credentials) {
                Single.fromCallable {
                    realmProvider.get().where(User::class.java)
                            .inQuery(User::id.name, userIds?.toLongArray() ?: longArrayOf(userId))
                            .findAll().toUsersResponse() ?: Response<List<User>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }


    override fun putUsers(users: Response<List<User>>): Single<Response<List<User>>> =
            with(preferencesRepo.credentials) {
                Single.fromCallable {
                    realmProvider.get().executeTransaction { it.insertOrUpdate(users.response) }
                    realmProvider.get().where(User::class.java)
                            .inQuery(User::id.name, users.toStringArray() ?: longArrayOf(userId))
                            .findAll().toUsersResponse() ?: Response<List<User>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun putMusicPage(response: MusicPage?, audioOffset: Int?): Completable = with(response!!) {
        Completable.create { e: CompletableEmitter? ->
            realmProvider.get().executeTransactionAsync(Realm.Transaction { realm ->
                run {
                    if (owner != null) realm.insertOrUpdate(owner) else return@run
                    if (playlists != null) {
                        playlists.userId = owner.id
                        realm.insertOrUpdate(playlists)
                    }
                    if (audios != null && audios.items.size > 0) {
                        audios.items.forEach {
                            it.album.thumb.id = it.id
                            realm.insertOrUpdate(it.album)
                        }
                        val realmAudios = realm.where(Audios::class.java)
                                .equalTo(Audios::userId.name, owner.id)
                                .findFirst()
                        if (realmAudios != null) {
                            //todo: remake to inversed order update
                            for (i in (audioOffset ?: 0)..(audioOffset ?: 0) + audios.items.size - 1) {
                                if (realmAudios.items.size > i) {
                                    if (realmAudios.items[i].id != audios.items[0].id) {
                                        val googlePhoto = realm.where(GooglePhoto::class.java)
                                                .equalTo(GooglePhoto::id.name, audios.items[0].id)
                                                .findFirst()
                                        if (googlePhoto != null)
                                            audios.items[0].googlePhoto = googlePhoto
                                        realmAudios.items[i] = audios.items.removeFirst()
                                    } else audios.items.removeFirst()
                                } else {
                                    realmAudios.items.addAll(audios.items)
                                    break
                                }
                            }
                        } else {
                            audios.userId = owner.id
                            realm.insertOrUpdate(audios)
                        }
                    }
                }
            }, Realm.Transaction.OnSuccess { e!!.onComplete() })
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun updateAudio(audio: Audio, cs: CustomSearch): Completable =
            with(realmProvider.get()) {
                Completable.fromAction {
                    executeTransaction {
                        audio.googlePhoto.id = audio.id
                        audio.googlePhoto.photo = cs.items?.get(0)?.link
                        it.insertOrUpdate(audio)
//                        it.insertOrUpdate(audio.googlePhoto)
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
}


