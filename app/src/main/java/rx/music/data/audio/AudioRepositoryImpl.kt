package rx.music.data.audio

import io.reactivex.Single
import me.extensions.toMd5
import rx.music.App
import rx.music.data.preferences.PreferencesRepository
import rx.music.network.BaseFields.Companion.HTTPS
import rx.music.network.BaseFields.Companion.LANG
import rx.music.network.BaseFields.Companion.V
import rx.music.network.apis.AudioApi
import rx.music.network.models.AudioResponse
import rx.music.network.models.BaseResponse
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepositoryImpl : AudioRepository {
    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var audioApi: AudioApi

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun getAudio(ownerId: String?, count: String,
                          offset: String): Single<BaseResponse<AudioResponse>> {
        return audioApi.getAudio(V, LANG, HTTPS,
                ownerId ?: preferencesRepository.credentials.user_id,
                count, offset, preferencesRepository.credentials.access_token,
                getSig(ownerId ?: preferencesRepository.credentials.user_id, count, offset))
    }

    private fun getSig(ownerId: String, count: String, offset: String): String {
        return ("/method/audio.get?v=${V}&lang=${LANG}&https=${HTTPS}&owner_id=$ownerId&count=$count&" +
                "offset=$offset&access_token=${preferencesRepository.credentials.access_token}" +
                preferencesRepository.credentials.secret).toMd5()
    }

    override val isNotAuthorized: Boolean get() = preferencesRepository.empty
}