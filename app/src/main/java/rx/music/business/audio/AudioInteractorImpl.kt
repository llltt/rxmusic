package rx.music.business.audio

import io.reactivex.Single
import rx.music.App
import rx.music.data.network.BaseFields.Companion.IMG_SIZE
import rx.music.data.network.models.Audio
import rx.music.data.network.models.AudioResponse
import rx.music.data.network.models.BaseResponse
import rx.music.data.repositories.audio.AudioRepository
import rx.music.data.repositories.google.GoogleRepository
import rx.music.data.repositories.mediaplayer.MediaPlayerRepository
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

    override fun getAudio(ownerId: String?, count: String, offset: String)
            : Single<BaseResponse<AudioResponse>> {
        return audioRepository.getAudio(ownerId, count, offset)
    }

    override fun handleAudio(audio: Audio): Single<Audio> {
        return googleRepository.getPicture(audio.artist, 1, IMG_SIZE)
                .doOnSubscribe { mediaPlayerRepository.play(audio).subscribe() }
                .doOnSuccess { pics -> audio.pic = pics?.items?.get(0)?.link }
                .map { audio }
    }

    override val isNotAuthorized: Boolean
        get() = audioRepository.isNotAuthorized
}
