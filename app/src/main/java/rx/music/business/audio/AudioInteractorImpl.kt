package rx.music.business.audio

import io.reactivex.Single
import rx.music.App
import rx.music.data.audio.AudioRepository
import rx.music.data.google.GoogleRepository
import rx.music.data.mediaplayer.MediaPlayerRepository
import rx.music.network.BaseFields.Companion.IMG_SIZE
import rx.music.network.models.Audio
import rx.music.network.models.AudioResponse
import rx.music.network.models.Base
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl : AudioInteractor {

    @Inject lateinit var audioRepository: AudioRepository
    @Inject lateinit var googleRepository: GoogleRepository
    @Inject lateinit var mediaPlayerRepository: MediaPlayerRepository

    var audio: Audio? = null

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int): Single<Base<AudioResponse>> =
            audioRepository.getAudio(ownerId, count, offset)

    override fun handleAudio(audio: Audio): Single<Audio> =
            googleRepository.getPicture(audio.artist, 1, IMG_SIZE)
                    .doOnSubscribe { mediaPlayerRepository.play(audio).subscribe() }
                    .doOnSuccess { pics -> audio.pic = pics?.items?.get(0)?.link }
                    .map { audio }

    override val isAuthorized: Boolean
        get() = audioRepository.isAuthorized
}
