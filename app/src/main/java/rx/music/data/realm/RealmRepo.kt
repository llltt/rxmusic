package rx.music.data.realm

import Items
import Response
import User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.net.models.audio.Audio
import rx.music.net.models.audio.CustomSearch

/** Created by Maksim Sukhotski on 5/9/2017. */

interface RealmRepo {
    fun putAudio(ownerId: Long?, audioResponse: Response<Items<MutableList<Audio>>>, count: Int, offset: Int): Completable
    fun putAudioToUser(ownerId: Long?, audioResponse: Response<Items<MutableList<Audio>>>, count: Int, offset: Int): Completable
    fun getAudio(ownerId: Long?): Observable<Response<Items<MutableList<Audio>>>>
    fun completeAudio(audio: Audio, customSearch: CustomSearch): Single<Audio>
    fun getUsers(userIds: String? = null): Single<Response<List<User>>>
    fun putUsers(users: Response<List<User>>): Single<Response<List<User>>>
}