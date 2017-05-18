package rx.music.data.mediaplayer

import android.media.AudioManager
import android.media.MediaPlayer
import io.reactivex.Completable
import rx.music.App
import rx.music.net.models.audio.Audio
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/5/2017. */
class MediaPlayerRepoImpl : MediaPlayerRepo {

    @Inject lateinit var mediaPlayer: MediaPlayer

    init {
        App.appComponent.inject(this)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnPreparedListener { it.start() }
    }

    var current: Audio? = null

    override fun play(audio: Audio): Completable = with(mediaPlayer) {
        Completable.fromAction {
            if (current?.id == audio.id && !isPlaying) start()
            else if (current?.id == audio.id && isPlaying) pause()
            else {
                current = audio
                reset()
                try {
                    setDataSource(audio.url)
                    prepareAsync()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }
    }
}