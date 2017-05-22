package rx.music.business.users

import io.reactivex.Observable
import rx.music.net.models.base.Response
import rx.music.net.models.vk.User

/** Created by Maksim Sukhotski on 5/14/2017. */
interface UsersInteractor {
    fun getAuthorized(): Observable<Response<List<User>>>
}