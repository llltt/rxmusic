package rx.music.data.vk

import io.reactivex.Observable
import io.reactivex.Single
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.data.realm.models.User
import rx.music.net.apis.VkApi
import rx.music.net.models.AudioResponse
import rx.music.net.models.Response
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class VkRepoImpl : VkRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var vkApi: VkApi

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int?): Observable<Response<AudioResponse>> =
            vkApi.getAudio(ownerId ?: preferencesRepo.credentials.user_id, count, offset)

    override fun getUsers(userIds: String?, fields: String?): Single<Response<List<User>>> =
            vkApi.getUsers(userIds ?: preferencesRepo.credentials.user_id.toString(), fields)

}