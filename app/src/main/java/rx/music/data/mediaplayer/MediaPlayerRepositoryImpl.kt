package rx.music.data.mediaplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.internal.operators.completable.CompletableFromAction
import rx.music.App
import rx.music.network.models.Audio
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/5/2017. */
class MediaPlayerRepositoryImpl : MediaPlayerRepository {

    @Inject lateinit var mediaPlayer: MediaPlayer

    init {
        App.appComponent.inject(this)
        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
            Log.d("ttt", "mp.start() -> ${mp.isPlaying}")
        }
    }

    var audio: Audio? = null

    override fun play(audio: Audio): Completable {
        return CompletableFromAction(Action {
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
        })

    }
}