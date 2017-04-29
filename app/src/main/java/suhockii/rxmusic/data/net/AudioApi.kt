package suhockii.rxmusic.data.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import suhockii.rxmusic.data.net.models.AudioResponse
import suhockii.rxmusic.data.net.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioApi {

    @Headers(
            "User-Agent: VKAndroidApp/4.0.1-816 (Android 6.0.1; SDK 23; armeabi-v7a; LGE Nexus 5; ru)"
    )
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