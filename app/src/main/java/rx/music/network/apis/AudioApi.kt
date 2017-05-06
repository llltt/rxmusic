package rx.music.network.apis

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.music.BuildConfig.USER_AGENT
import rx.music.network.models.AudioResponse
import rx.music.network.models.Base

/** Created by Maksim Sukhotski on 4/9/2017. */
interface AudioApi {
    @Headers("User-Agent: $USER_AGENT")
    @GET("audio.get")
    fun getAudio(@Query("v") v: Double,
                 @Query("lang") lang: String,
                 @Query("https") https: Int,
                 @Query("owner_id") ownerId: Long,
                 @Query("count") count: Int,
                 @Query("offset") offset: Int,
                 @Query("access_token") accessToken: String,
                 @Query("sig") sig: String): Single<Base<AudioResponse>>
}