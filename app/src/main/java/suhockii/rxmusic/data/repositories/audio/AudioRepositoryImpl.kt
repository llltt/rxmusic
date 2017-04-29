package suhockii.rxmusic.data.repositories.audio

import io.reactivex.Single
import suhockii.rxmusic.data.net.AudioApi
import suhockii.rxmusic.data.net.BaseFields.Companion.HTTPS
import suhockii.rxmusic.data.net.BaseFields.Companion.LANG
import suhockii.rxmusic.data.net.BaseFields.Companion.V
import suhockii.rxmusic.data.net.RetrofitObject
import suhockii.rxmusic.data.net.models.AudioResponse
import suhockii.rxmusic.data.net.models.BaseResponse
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.extensions.toMd5

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