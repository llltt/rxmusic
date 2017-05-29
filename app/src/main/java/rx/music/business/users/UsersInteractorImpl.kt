package rx.music.business.users

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
}