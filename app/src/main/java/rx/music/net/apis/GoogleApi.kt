package rx.music.net.apis

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import rx.music.net.models.audio.CustomSearch

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
}