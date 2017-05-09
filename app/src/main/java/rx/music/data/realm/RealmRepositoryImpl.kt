package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import javax.inject.Inject
import javax.inject.Provider

/** Created by Maksim Sukhotski on 5/9/2017. */
class RealmRepositoryImpl @Inject constructor(private var realmProvider: Provider<Realm>) : RealmRepository {
    override fun putAudio(items: List<Audio>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int): Single<Base<AudioResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}