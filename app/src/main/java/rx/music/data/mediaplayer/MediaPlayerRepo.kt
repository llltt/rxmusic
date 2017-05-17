package rx.music.data.mediaplayer

import io.reactivex.Completable
import rx.music.data.realm.models.Audio

/** Created by Maksim Sukhotski on 5/5/2017. */
interface MediaPlayerRepo {
    fun play(audio: Audio): Completable
}