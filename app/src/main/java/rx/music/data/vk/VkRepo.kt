package rx.music.data.vk

import Items
import Response
import User
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.net.BaseFields.Companion.MIN_USER_INFO
import rx.music.net.models.audio.Audio
import rx.music.net.models.audio.MusicPage

/** Created by Maksim Sukhotski on 4/9/2017. */
interface VkRepo {
    fun getAudio(ownerId: Long?, count: Int, offset: Int? = 0): Observable<Response<Items<MutableList<Audio>>>>

    fun getMusicPage(ownerId: Long, audioCount: Int, audioOffset: Int): Single<Response<MusicPage>>

    fun getUsers(userIds: String? = null, fields: String? = MIN_USER_INFO): Single<Response<List<User>>>
}