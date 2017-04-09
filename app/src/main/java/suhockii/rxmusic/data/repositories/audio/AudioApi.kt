package suhockii.rxmusic.data.repositories.audio

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import suhockii.rxmusic.data.net.models.BaseResponse
import suhockii.rxmusic.data.repositories.audio.models.AudioResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioApi {

    @GET("audio.get")
    fun getAudio(@Query("v") v: String,
                 @Query("lang") lang: String,
                 @Query("https") https: String,
                 @Query("owner_id") ownerId: String,
                 @Query("count") count: String,
                 @Query("offset") offset: String,
                 @Query("access_token") accessToken: String,
                 @Query("sig") sig: String): Single<BaseResponse<AudioResponse>>
}