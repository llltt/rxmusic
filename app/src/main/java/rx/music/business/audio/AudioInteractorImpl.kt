package rx.music.business.audio

import io.reactivex.Observable
import io.reactivex.Single
import rx.music.dagger.Dagger
import rx.music.data.audio.AudioRepo
import rx.music.data.google.GoogleRepo
import rx.music.data.mediaplayer.MediaPlayerRepo
import rx.music.data.realm.RealmRepo
import rx.music.net.BaseFields.Companion.IMG_SIZE
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl : AudioInteractor {

    @Inject lateinit var audioRepo: AudioRepo
    @Inject lateinit var realmRepo: RealmRepo
    @Inject lateinit var googleRepo: GoogleRepo
    @Inject lateinit var mediaPlayerRepo: MediaPlayerRepo

    var audio: Audio? = null

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int): Observable<Base<AudioResponse>> =
            Observable.concat(realmRepo.getAudio(ownerId),
                    audioRepo.getAudio(ownerId, count, offset)
                            .doOnNext { realmRepo.putAudio(it).subscribe() })

    override fun handleAudio(audio: Audio): Single<Audio> =
            mediaPlayerRepo.play(audio)
                    .andThen(googleRepo.getPicture(audio.artist, 1, IMG_SIZE)
                            .flatMap { pic -> realmRepo.completeAudio(audio, pic) })

    override val isAuthorized: Boolean get() = audioRepo.isAuthorized
}
