package rx.music.net.apis

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.music.BuildConfig.USER_AGENT
import rx.music.data.realm.models.User
import rx.music.net.models.AudioResponse
import rx.music.net.models.Response

/** Created by Maksim Sukhotski on 4/9/2017. */
interface VkApi {
    @FormUrlEncoded
    @Headers("User-Agent: $USER_AGENT")
    @POST("audio.get")
    fun getAudio(@Field("owner_id") ownerId: Long,
                 @Field("count") count: Int,
                 @Field("offset") offset: Int?): Observable<Response<AudioResponse>>

    @FormUrlEncoded
    @POST("users.get")
    fun getUsers(@Field("user_ids") userIds: String,
                 @Field("fields") fields: String?): Single<Response<List<User>>>
}