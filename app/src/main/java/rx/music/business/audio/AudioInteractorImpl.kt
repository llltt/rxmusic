package rx.music.business.audio

import android.media.AudioManager
import android.media.MediaPlayer
import io.reactivex.Single
import rx.music.App
import rx.music.data.network.BaseFields.Companion.IMG_SIZE
import rx.music.data.network.models.Audio
import rx.music.data.network.models.AudioResponse
import rx.music.data.network.models.BaseResponse
import rx.music.data.network.models.CustomSearch
import rx.music.data.repositories.audio.AudioRepository
import rx.music.data.repositories.google.GoogleRepository
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl : AudioInteractor {

    @Inject lateinit var audioRepository: AudioRepository
    @Inject lateinit var googleRepository: GoogleRepository

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var audio: Audio? = null

    init {
        App.instance.userComponent?.inject(this)
        mediaPlayer.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
    }

    override fun getAudio(ownerId: String?, count: String, offset: String)
            : Single<BaseResponse<AudioResponse>> {
        return audioRepository.getAudio(ownerId, count, offset)
    }

    override fun handleAudio(audio: Audio): Single<Audio> {
        if (this.audio?.id == audio.id)
            if (mediaPlayer.isPlaying) mediaPlayer.pause()
            else mediaPlayer.start()
        else {
            this.audio = audio
            mediaPlayer.reset()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(audio.url)
            mediaPlayer.prepareAsync()
        }
        return googleRepository.getPicture(audio.artist, 1, IMG_SIZE)
                .doOnSuccess { t: CustomSearch? ->
                    run {
                        audio.pic = t?.items?.get(0)?.link
                        audio.pic_preview = t?.items?.get(0)?.thumbnailLink
                    }
                }.map { audio }
    }

    override val isNotAuthorized: Boolean
        get() = audioRepository.isNotAuthorized
}
