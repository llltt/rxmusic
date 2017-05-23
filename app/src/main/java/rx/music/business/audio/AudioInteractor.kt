package rx.music.business.audio

import io.reactivex.Single
import rx.music.net.models.base.Response
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.MusicPage

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioInteractor {
//    fun getAudio(ownerId: Long?, count: Int, offset: Int):
//            Observable<Response<Items<MutableList<Audio>>>>

    fun getMusicPage(ownerId: Long? = null, audioCount: Int? = null, audioOffset: Int? = null):
            Single<Response<MusicPage>>

    fun handleAudio(audio: Audio): Single<Audio>
}