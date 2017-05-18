package rx.music.business.users

import Response
import User
import io.reactivex.Observable

/** Created by Maksim Sukhotski on 5/14/2017. */
interface UsersInteractor {
    fun getAuthorized(): Observable<Response<List<User>>>
}