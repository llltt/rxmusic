package rx.music.business.users

import io.reactivex.Observable
import rx.music.data.realm.models.User
import rx.music.net.models.Response

/** Created by Maksim Sukhotski on 5/14/2017. */
interface UsersInteractor {
    fun getAuthorized(): Observable<Response<List<User>>>
}