package suhockii.rxmusic.data.repositories.audio

import io.reactivex.Single
import suhockii.rxmusic.App
import suhockii.rxmusic.data.net.AudioApi
import suhockii.rxmusic.data.net.ConstantFields.Companion.HTTPS
import suhockii.rxmusic.data.net.ConstantFields.Companion.LANG
import suhockii.rxmusic.data.net.ConstantFields.Companion.V
import suhockii.rxmusic.data.net.RetrofitObject
import suhockii.rxmusic.data.net.models.BaseResponse
import suhockii.rxmusic.data.repositories.audio.models.AudioResponse
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.extension.md5
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioRepositoryImpl : AudioRepository {

    @Inject lateinit var repository: PreferencesRepository

    init {  //todo -> maybe init block is overhead
        App.appComponent.inject(this)
    }

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
                repository.credentials.access_token,
                md5("/method/audio.get?" +
                        "v=$V&" +
                        "lang=$LANG&" +
                        "https=$HTTPS&" +
                        "owner_id=$ownerId&" +
                        "count=$count&" +
                        "offset=$offset&" +
                        "access_token=${repository.credentials.access_token}${repository.credentials.secret}"))
    }
}