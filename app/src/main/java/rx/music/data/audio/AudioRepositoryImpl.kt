package rx.music.data.audio

import io.reactivex.Single
import me.extensions.toMd5
import rx.music.App
import rx.music.data.preferences.PreferencesRepository
import rx.music.net.BaseFields.Companion.HTTPS
import rx.music.net.BaseFields.Companion.LANG
import rx.music.net.BaseFields.Companion.V
import rx.music.net.apis.AudioApi
import rx.music.net.models.AudioResponse
import rx.music.net.models.Base
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepositoryImpl : AudioRepository {
    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var audioApi: AudioApi

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: Long?, count: Int, offset: Int): Single<Base<AudioResponse>> =
            audioApi.getAudio(V, LANG, HTTPS, ownerId ?: preferencesRepository.credentials.user_id,
                    count, offset, preferencesRepository.credentials.access_token,
                    getSig(ownerId ?: preferencesRepository.credentials.user_id, count, offset))

    private fun getSig(ownerId: Long, count: Int, offset: Int): String =
            ("/method/audio.get?v=$V&lang=$LANG&https=$HTTPS&owner_id=$ownerId&count=$count&" +
                    "offset=$offset&access_token=${preferencesRepository.credentials.access_token}" +
                    preferencesRepository.credentials.secret).toMd5()

    override val isAuthorized: Boolean get() = preferencesRepository.isNotEmpty
}