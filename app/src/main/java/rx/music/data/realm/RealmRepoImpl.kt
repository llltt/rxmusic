package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import me.extensions.inQuery
import me.extensions.toAudioResponse
import me.extensions.toUserIds
import me.extensions.toUsersResponse
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.data.realm.models.Audio
import rx.music.data.realm.models.User
import rx.music.net.models.AudioResponse
import rx.music.net.models.CustomSearch
import rx.music.net.models.Response
import javax.inject.Inject
import javax.inject.Provider


/** Created by Maksim Sukhotski on 5/9/2017. */

class RealmRepoImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun putAudioWithUserUpdate(ownerId: Long?, audioResponse: Response<AudioResponse>,
                                        count: Int, offset: Int): Completable =
            with(preferencesRepo.credentials) {
                Completable.fromCallable {
                    realmProvider.get().executeTransactionAsync {
                        it.insertOrUpdate(audioResponse.response?.items)
                        val user = it.where(User::class.java).equalTo(User::id.name, ownerId ?: user_id).findFirst()
                        audioResponse.response?.items?.forEachIndexed { index, audio -> audio.pos = offset + index }
                        user?.audioList?.removeAll { it.pos > offset && it.pos < offset + count }
                        user?.audioList?.addAll(offset, audioResponse.response?.items ?: arrayListOf())
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
            with(preferencesRepo.credentials) {
                Single.fromCallable {
                    realmProvider.get().where(User::class.java)
                            .inQuery(User::id.name, userIds?.toUserIds() ?: longArrayOf(user_id))
                            .findAll().toUsersResponse() ?: Response<List<User>>()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }


    override fun putUsers(users: Response<List<User>>): Completable =
            Completable.fromAction {
                realmProvider.get().executeTransactionAsync { it.insertOrUpdate(users.response) }
            }.subscribeOn(AndroidSchedulers.mainThread())
}
