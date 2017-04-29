package suhockii.rxmusic.business.audio

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Single
import suhockii.rxmusic.App
import suhockii.rxmusic.data.net.models.Audio
import suhockii.rxmusic.data.net.models.AudioResponse
import suhockii.rxmusic.data.net.models.BaseResponse
import suhockii.rxmusic.data.repositories.audio.AudioRepository

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioInteractorImpl(private val audioRepository: AudioRepository) : AudioInteractor {
    val mediaPlayer: MediaPlayer = MediaPlayer()

    override fun getAudio(ownerId: String, count: String, offset: String): Single<BaseResponse<AudioResponse>> {
        return audioRepository.getAudio(ownerId, count, offset)
    }

    override fun startPlaying(audio: Audio): Completable {
        return Completable.fromAction {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(App.instance, Uri.parse(audio.url))
            mediaPlayer.prepare()
            mediaPlayer.start()
        }
    }


}