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
    @Headers("Authorization: ${BuildConfig.GCM_AUTHORIZATION}")
    @POST
    fun register(@Url url: String,
                 @Field("X-subtype") Xsubtype: String,
                 @Field("X-X-subscription") XXsubscription: String,
                 @Field("X-X-subtype") XXsubtype: String,
                 @Field("X-app_ver") Xapp_ver: String,
                 @Field("X-kid") Xkid: String,
                 @Field("X-osv") Xosv: String,
                 @Field("X-sig") Xsig: String,
                 @Field("X-cliv") Xcliv: String,
                 @Field("X-gmsv") Xgmsv: String,
                 @Field("X-pub2") Xpub2: String,
                 @Field("X-X-kid") XXkid: String,
                 @Field("X-appid") Xappid: String,
                 @Field("X-scope") Xscope: String,
                 @Field("X-subscription") Xsubscription: String,
                 @Field("X-app_ver_name") Xapp_ver_name: String,
                 @Field("app") app: String,
                 @Field("sender") sender: String,
                 @Field("device") device: String,
                 @Field("cert") cert: String,
                 @Field("app_ver") app_ver: String,
                 @Field("info") info: String,
                 @Field("gcm_ver") gcm_ver: String): Single<GcmToken>
}