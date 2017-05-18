package rx.music.data.vk

import Items
import Response
import User
import io.reactivex.Observable
import io.reactivex.Single
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.apis.VkApi
import rx.music.net.models.audio.Audio
import rx.music.net.models.audio.MusicPage
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class VkRepoImpl : VkRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var vkApi: VkApi

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int,
                          offset: Int?): Observable<Response<Items<MutableList<Audio>>>> =
            with(preferencesRepo.credentials) {
                vkApi.getAudio(ownerId ?: user_id, count, offset)
            }

    override fun getMusicPage(ownerId: Long, audioCount: Int, audioOffset: Int): Single<Response<MusicPage>> =
            with(preferencesRepo.credentials) {
                vkApi.getMusicPage(ownerId = ownerId,
                        audioCount = audioCount, audioOffset = audioOffset)
            }

    override fun getUsers(userIds: String?, fields: String?): Single<Response<List<User>>> =
            vkApi.getUsers(userIds ?: preferencesRepo.credentials.user_id.toString(), fields)

}