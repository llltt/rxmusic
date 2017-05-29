package rx.music.data.realm

import io.reactivex.Completable
import io.reactivex.Single
import rx.music.net.models.base.Response
import rx.music.net.models.google.CustomSearch
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.MusicPage
import rx.music.net.models.vk.User

/** Created by Maksim Sukhotski on 5/9/2017. */

interface RealmRepo {
    fun updateAudio(audio: Audio, cs: CustomSearch): Completable
    fun getUsers(userIds: String? = null): Single<Response<List<User>>>
    fun putUsers(users: Response<List<User>>): Single<Response<List<User>>>
    fun putMusicPage(response: MusicPage?, audioOffset: Int?): Completable
    fun getAudioCountForRequest(ownerId: Long?, audioOffset: Int?): Single<Int>
}