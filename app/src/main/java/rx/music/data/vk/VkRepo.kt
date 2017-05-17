package rx.music.data.vk

import io.reactivex.Observable
import io.reactivex.Single
import rx.music.data.realm.models.User
import rx.music.net.BaseFields.Companion.MIN_USER_INFO
import rx.music.net.models.AudioResponse
import rx.music.net.models.Response

/** Created by Maksim Sukhotski on 4/9/2017. */
interface VkRepo {
    fun getAudio(ownerId: Long?, count: Int, offset: Int? = 0): Observable<Response<AudioResponse>>
    fun getUsers(userIds: String? = null, fields: String? = MIN_USER_INFO): Single<Response<List<User>>>
}