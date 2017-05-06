package rx.music.network.apis

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import rx.music.network.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/

interface AuthApi {
    @GET
    fun getCredentials(@Url url: String,
                       @Query("scope") scope: String,
                       @Query("client_id") clientId: Int,
                       @Query("client_secret") clientSecret: String,
                       @Query("2fa_supported") twoFaSupported: Int,
                       @Query("lang") lang: String,
                       @Query("grant_type") grantType: String,
                       @Query("libverify_support") libverifySupport: Int,
                       @Query("username") username: String,
                       @Query("password") password: String,
                       @Query("captcha_sid") captcha_sid: String?,
                       @Query("captcha_key") captcha: String?,
                       @Query("code") code: String?): Single<Credentials>

    @GET("auth.validatePhone")
    fun validatePhone(@Query("v") v: Double,
                      @Query("lang") lang: String,
                      @Query("https") https: Int,
                      @Query("sid") sid: String,
                      @Query("api_id") api_id: Int): Completable
}