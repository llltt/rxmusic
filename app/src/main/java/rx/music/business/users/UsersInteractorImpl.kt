package rx.music.business.users

import Response
import User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import rx.music.dagger.Dagger
import rx.music.data.realm.RealmRepo
import rx.music.data.vk.VkRepo
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/14/2017. */
class UsersInteractorImpl : UsersInteractor {
    @Inject lateinit var vkRepo: VkRepo
    @Inject lateinit var realmRepo: RealmRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAuthorized(): Observable<Response<List<User>>> = Observable.concat(
            realmRepo.getUsers()
                    .toObservable(),
            vkRepo.getUsers()
                    .subscribeOn(Schedulers.io())
                    .flatMap { realmRepo.putUsers(it) }
                    .toObservable()
    )
}