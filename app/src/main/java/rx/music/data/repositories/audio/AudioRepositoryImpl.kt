package rx.music.data.repositories.audio

import io.reactivex.Single
import me.ext.toMd5
import rx.music.data.net.AudioApi
import rx.music.data.net.BaseFields.Companion.HTTPS
import rx.music.data.net.BaseFields.Companion.LANG
import rx.music.data.net.BaseFields.Companion.V
import rx.music.data.net.RetrofitObject
import rx.music.data.net.models.AudioResponse
import rx.music.data.net.models.BaseResponse
import rx.music.data.repositories.preferences.PreferencesRepository

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepositoryImpl(private val preferencesRepository: PreferencesRepository) : AudioRepository {

    internal val api = RetrofitObject.build(AudioApi::class.java)

    override fun getAudio(ownerId: String,
                          count: String,
                          offset: String): Single<BaseResponse<AudioResponse>> {
        return api.getAudio(V,
                LANG,
                HTTPS,
                ownerId,
                count,
                offset,
                preferencesRepository.credentials.access_token,
                getSig(ownerId, count, offset))
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
}