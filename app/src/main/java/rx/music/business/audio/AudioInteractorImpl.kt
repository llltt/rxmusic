package rx.music.business.audio

import io.reactivex.Observable
import io.reactivex.Single
import rx.music.dagger.Dagger
import rx.music.data.google.GoogleRepo
import rx.music.data.mediaplayer.MediaPlayerRepo
import rx.music.data.realm.RealmRepo
import rx.music.data.realm.models.Audio
import rx.music.data.vk.VkRepo
import rx.music.net.BaseFields.Companion.IMG_SIZE
import rx.music.net.models.AudioResponse
import rx.music.net.models.Response
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

    override fun getAudio(ownerId: Long?, count: Int, offset: Int)
            : Observable<Response<AudioResponse>> =
            Observable.concat(vkRepo.getAudio(ownerId, count, offset).doOnNext {
                realmRepo.putAudioWithUserUpdate(ownerId, it, count, offset)
                        .subscribe()
            }, realmRepo.getAudio(ownerId))

    override fun handleAudio(audio: Audio): Single<Audio> =
            mediaPlayerRepo.play(audio).andThen(
                    if (audio.pic.isNotEmpty()) Single.fromCallable { audio }
                    else googleRepo.getPicture(audio.artist, 1, IMG_SIZE)
                            .flatMap { realmRepo.completeAudio(audio, it) })
                    .onErrorResumeNext { Single.fromCallable { audio } }
}
