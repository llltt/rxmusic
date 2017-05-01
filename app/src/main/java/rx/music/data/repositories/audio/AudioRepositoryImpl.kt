package rx.music.data.repositories.audio

import io.reactivex.Single
import me.ext.toMd5
import rx.music.App
import rx.music.data.net.AudioApi
import rx.music.data.net.BaseFields.Companion.HTTPS
import rx.music.data.net.BaseFields.Companion.LANG
import rx.music.data.net.BaseFields.Companion.V
import rx.music.data.net.RetrofitObject
import rx.music.data.net.models.AudioResponse
import rx.music.data.net.models.BaseResponse
import rx.music.data.repositories.preferences.PreferencesRepository
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepositoryImpl : AudioRepository {

    @Inject lateinit var preferencesRepository: PreferencesRepository

    init {
        App.instance.userComponent?.inject(this)
    }
    internal val api = RetrofitObject.build(AudioApi::class.java)

    override fun getAudio(ownerId: String?,
                          count: String,
                          offset: String): Single<BaseResponse<AudioResponse>> {
        val handledOwnerId = ownerId ?: preferencesRepository.credentials.user_id
        return api.getAudio(V,
                LANG,
                HTTPS,
                handledOwnerId,
                count,
                offset,
                preferencesRepository.credentials.access_token,
                getSig(handledOwnerId, count, offset))
    }

    private fun getSig(ownerId: String, count: String, offset: String): String {
        return ("/method/audio.get?" +
                "v=$V&" +
                "lang=$LANG&" +
                "https=$HTTPS&" +
                "owner_id=$ownerId&" +
                "count=$count&" +
                "offset=$offset&" +
                "access_token=${preferencesRepository.credentials.access_token}" +
                preferencesRepository.credentials.secret)
                .toMd5()
    }

    override val isNotAuthorized: Boolean
        get() = preferencesRepository.empty
}