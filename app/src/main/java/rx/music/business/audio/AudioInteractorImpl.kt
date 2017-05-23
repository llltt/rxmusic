package rx.music.business.audio

import io.reactivex.Single
import me.extensions.tokenNotConfirmed
import rx.music.dagger.Dagger
import rx.music.data.google.GoogleRepo
import rx.music.data.mediaplayer.MediaPlayerRepo
import rx.music.data.realm.RealmRepo
import rx.music.data.vk.VkRepo
import rx.music.net.BaseFields.Companion.IMG_SIZE
import rx.music.net.models.base.Response
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.MusicPage
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl : AudioInteractor {
    @Inject lateinit var vkRepo: VkRepo
    @Inject lateinit var realmRepo: RealmRepo
    @Inject lateinit var googleRepo: GoogleRepo
    @Inject lateinit var mediaPlayerRepo: MediaPlayerRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

//    override fun getAudio(ownerId: Long?, count: Int, offset: Int):
//            Observable<Response<Items<MutableList<Audio>>>> =
//            Observable.concat(
//                    vkRepo.getAudio(ownerId, count, offset)
//                            .doOnNext {
//                                if (it.response.isNotNull) {
//                                    realmRepo.putAudio(ownerId, it, count, offset)
//                                            .subscribe()
//                                    realmRepo.putAudioToUser(ownerId, it, count, offset)
//                                            .subscribe()
//                                }
//                            },
//                    realmRepo.getAudio(ownerId))

    override fun getMusicPage(ownerId: Long?, audioCount: Int?, audioOffset: Int?):
            Single<Response<MusicPage>> = vkRepo
            .getMusicPage(ownerId, audioCount, audioOffset)
            .flatMap {
                if (it.tokenNotConfirmed) googleRepo
                        .register()
                        .flatMap { vkRepo.refreshToken(it.token ?: "") }
                        .flatMap { vkRepo.getMusicPage(ownerId, audioCount, audioOffset) }
                else Single.fromCallable { it }
            }
            .doOnSuccess { realmRepo.putMusicPage(it.response).subscribe() }

    override fun handleAudio(audio: Audio): Single<Audio> = mediaPlayerRepo
            .play(audio)
            .andThen(
                    if (audio.googleThumb.isNotEmpty()) Single.fromCallable { audio }
                    else googleRepo
                            .getPicture(audio.artist, 1, IMG_SIZE)
                            .flatMap { realmRepo.completeAudio(audio, it) })
            .onErrorResumeNext { Single.fromCallable { audio } }
}

