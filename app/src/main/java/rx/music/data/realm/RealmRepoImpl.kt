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
                    executeTransactionAsync { it.insertOrUpdate(audio.response?.items) }
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

    override fun getUsers(userIds: String?): Single<Response<List<User>>> = Single.fromCallable {
        realmProvider.get().where(User::class.java)
                .inQuery(User::id.name, userIds?.toUserIds())
                .findAll().toUsersResponse() ?: Response<List<User>>()
    }.subscribeOn(AndroidSchedulers.mainThread())


    override fun putUsers(users: Response<List<User>>): Completable = with(realmProvider.get()) {
        Completable.fromAction { executeTransactionAsync { it.insertOrUpdate(users.response) } }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun updateUserAudio(ownerId: Long?, audio: Response<AudioResponse>, count: Int,
                                 offset: Int): Completable = with(preferencesRepo.credentials) {
        Completable.fromAction {
            realmProvider.get().executeTransactionAsync {
                val user = realmProvider.get().where(User::class.java)
                        .equalTo(User::id.name, ownerId ?: user_id).findFirst()
                user.audioList.removeAll { it.pos > offset && it.pos < offset + count }
                user.audioList.addAll(offset, audio.response?.items ?: arrayListOf())
                realmProvider.get().insertOrUpdate(user)
            }
        }.subscribeOn(AndroidSchedulers.mainThread())
    }


}
