package rx.music.business.audio

import android.media.AudioManager
import android.media.MediaPlayer
import io.reactivex.Completable
import io.reactivex.Single
import rx.music.data.net.models.Audio
import rx.music.data.net.models.AudioResponse
import rx.music.data.net.models.BaseResponse
import rx.music.data.repositories.audio.AudioRepository

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl(private val audioRepository: AudioRepository) : AudioInteractor {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var audio: Audio? = null

    override fun getAudio(ownerId: String, count: String, offset: String): Single<BaseResponse<AudioResponse>> {
        return audioRepository.getAudio(ownerId, count, offset)
    }


    override fun playAudio(audio: Audio): Completable {
        return Completable.fromAction {
            if (this.audio?.id == audio.id)
                if (mediaPlayer.isPlaying) mediaPlayer.pause()
                else mediaPlayer.start()
            else {
                this.audio = audio
                mediaPlayer.reset()
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer.setDataSource(audio.url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }
    }
}