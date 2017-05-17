package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.data.realm.models.Audio
import rx.music.data.realm.models.User
import rx.music.net.models.AudioResponse
import rx.music.net.models.CustomSearch
import rx.music.net.models.Response

/** Created by Maksim Sukhotski on 5/9/2017. */

interface RealmRepo {
    fun putAudioWithUserUpdate(ownerId: Long?, audioResponse: Response<AudioResponse>, count: Int, offset: Int): Completable
    fun getAudio(ownerId: Long?): Observable<Response<AudioResponse>>
    fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio>
    fun getUsers(userIds: String? = null): Single<Response<List<User>>>
    fun putUsers(users: Response<List<User>>): Completable
}