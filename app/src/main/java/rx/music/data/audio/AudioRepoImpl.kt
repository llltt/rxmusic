package rx.music.data.audio

import io.reactivex.Observable
import me.extensions.toMd5
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.BaseFields.Companion.HTTPS
import rx.music.net.BaseFields.Companion.LANG
import rx.music.net.BaseFields.Companion.V
import rx.music.net.apis.AudioApi
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepoImpl : AudioRepo {
    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var audioApi: AudioApi

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int): Observable<Base<AudioResponse>> =
            audioApi.getAudio(V, LANG, HTTPS, ownerId ?: preferencesRepo.credentials.user_id,
                    count, offset, preferencesRepo.credentials.access_token,
                    getSig(ownerId ?: preferencesRepo.credentials.user_id, count, offset))

    private fun getSig(ownerId: Long, count: Int, offset: Int): String =
            ("/method/audio.get?v=$V&lang=$LANG&https=$HTTPS&owner_id=$ownerId&count=$count&" +
                    "offset=$offset&access_token=${preferencesRepo.credentials.access_token}" +
                    preferencesRepo.credentials.secret).toMd5()

    override val isAuthorized: Boolean get() = preferencesRepo.isNotEmpty
}