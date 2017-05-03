package rx.music.data.network.apis

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.music.BuildConfig.USER_AGENT
import rx.music.data.network.models.AudioResponse
import rx.music.data.network.models.BaseResponse

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioApi {
    @Headers("User-Agent: $USER_AGENT")
    @GET("audio.get")
    fun getAudio(@Query("v") v: String,
                 @Query("lang") lang: String,
                 @Query("https") https: String,
                 @Query("owner_id") ownerId: String,
                 @Query("count") count: String,
                 @Query("offset") offset: String,
                 @Query("access_token") accessToken: String,
                 @Query("sig") sig: String): io.reactivex.Single<BaseResponse<AudioResponse>>
}