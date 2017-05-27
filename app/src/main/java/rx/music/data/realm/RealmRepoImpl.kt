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
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.Audios
import rx.music.net.models.vk.MusicPage
import rx.music.net.models.vk.User
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

//    override fun putAudioToUser(ownerId: Long?, audioResponse: Response<Items<MutableList<Audio>>>,
//                                count: Int, offset: Int): Completable =
//            with(preferencesRepo.credentials) {
//                Completable.fromCallable {
//                    realmProvider.get().executeTransactionAsync {
//                        val user = it.where(User::class.java)
//                                .equalTo(User::id.name, ownerId ?: userId)
//                                .findFirst()
//                        audioResponse.response?.items?.forEachIndexed { index, audio ->
//                            audio.pos = offset + index
//                        }
//                        user.audioList.removeAll { it.pos > offset && it.pos < offset + count }
//                        user.audioList.addAll(offset, audioResponse.response?.items ?: arrayListOf())
//                    }
//                }.subscribeOn(AndroidSchedulers.mainThread())
//            }

    override fun getAudio(ownerId: Long?): Observable<Response<Items<MutableList<Audio>>>> =
            with(realmProvider.get()) {
                Observable.fromCallable {
                    where(Audio::class.java).findAll()?.toAudioResponse() ?: Response<Items<MutableList<Audio>>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }

    override fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio> =
            with(realmProvider.get()) {
                Single.fromCallable {
                    val t = where(Audio::class.java).equalTo(Audio::id.name, audio.id).findFirst()
                    executeTransaction { t.googleThumb = customSearch.items?.get(0)?.link ?: "" }
                    return@fromCallable copyFromRealm(t)
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
                    if (owner != null) {
                        realm.insertOrUpdate(owner)
                        if (audios != null && audios.items.size > 0) {
                            val realmAudios = realm.where(Audios::class.java)
                                    .equalTo(Audios::userId.name, owner.id)
                                    .findFirst()
                            if (realmAudios != null) {
                                for (i in (audioOffset ?: 0)..(audioOffset ?: 0) + audios.items.size - 1) {
                                    audios.items.forEach {
                                        it.album.thumb.id = it.id
                                        realm.insertOrUpdate(it.album)
                                        realm.insertOrUpdate(it.album.thumb)
                                    }
                                    if (realmAudios.items.size > i) {
                                        if (realmAudios.items[i].id != audios.items[0].id)
                                            realmAudios.items[i] = audios.items.removeFirst()
                                        else audios.items.removeFirst()
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
                        if (playlists != null) {
                            playlists.userId = owner.id
                            realm.insertOrUpdate(playlists)
                        }
                    }
                }
            }, Realm.Transaction.OnSuccess { e!!.onComplete() })
        }
                .subscribeOn(AndroidSchedulers.mainThread())
    }
}


