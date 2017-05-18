package rx.music.net.apis

import Items
import Response
import User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.music.BuildConfig.USER_AGENT
import rx.music.net.models.audio.Audio
import rx.music.net.models.audio.MusicPage

/** Created by Maksim Sukhotski on 4/9/2017. */
interface VkApi {
    @FormUrlEncoded
    @Headers("User-Agent: $USER_AGENT")
    @POST("audio.get")
    fun getAudio(@Field("owner_id") ownerId: Long,
                 @Field("count") count: Int,
                 @Field("offset") offset: Int?): Observable<Response<Items<MutableList<Audio>>>>

    @FormUrlEncoded
    @POST("execute.getMusicPage")
    fun getMusicPage(@Field("owner_id") ownerId: Long? = null,
                     @Field("need_owner") needOwner: Int? = 1,
                     @Field("need_playlists") needPlaylists: Int? = 1,
                     @Field("playlists_count") playlistsCount: Int? = 12,
                     @Field("audio_offset") audioOffset: Int? = 0,
                     @Field("audio_count") audioCount: Int? = 100): Single<Response<MusicPage>>

    @FormUrlEncoded
    @POST("users.get")
    fun getUsers(@Field("user_ids") userIds: String,
                 @Field("fields") fields: String?): Single<Response<List<User>>>
}