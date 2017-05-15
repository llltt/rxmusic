package rx.music.business.users

import io.reactivex.Observable
import rx.music.dagger.Dagger
import rx.music.data.realm.RealmRepo
import rx.music.data.vk.VkRepo
import rx.music.net.models.Response
import rx.music.net.models.User
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/14/2017. */
class UsersInteractorImpl : UsersInteractor {
    @Inject lateinit var vkRepo: VkRepo
    @Inject lateinit var realmRepo: RealmRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAuthorized(): Observable<Response<List<User>>> = Observable.concat(
            vkRepo.getUsers().toObservable().doOnNext { realmRepo.putUsers(it).subscribe() },
            realmRepo.getUsers().toObservable())
}