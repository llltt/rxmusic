package rx.music.net.apis

import io.reactivex.Single
import retrofit2.http.*
import rx.music.BuildConfig
import rx.music.net.models.google.CustomSearch
import rx.music.net.models.google.GcmToken

/** Created by Maksim Sukhotski on 4/9/2017. */
interface GoogleApi {
    @GET
    fun getPicture(@Url url: String,
                   @Query("q") q: String,
                   @Query("num") num: Int,
                   @Query("imgSize") imgSize: String,
                   @Query("searchType") searchType: String,
                   @Query("key") key: String,
                   @Query("cx") cx: String): Single<CustomSearch>

    @FormUrlEncoded
    @Headers("Authorization: ${BuildConfig.C2DM_AUTHORIZATION}")
    @POST
    fun register(@Url url: String,
                 @Field("app") app: String?,
                 @Field("sender") sender: String?,
                 @Field("device") device: String?): Single<GcmToken>
}