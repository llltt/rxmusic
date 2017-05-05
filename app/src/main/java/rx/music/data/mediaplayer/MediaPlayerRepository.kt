package rx.music.data.mediaplayer

import io.reactivex.Completable
import rx.music.network.models.Audio

/** Created by Maksim Sukhotski on 5/5/2017. */
interface MediaPlayerRepository {
    fun play(audio: Audio): Completable
}