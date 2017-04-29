package suhockii.rxmusic.data.net

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import suhockii.rxmusic.data.net.models.Credentials

/** Created by Maksim Sukhotski on 3/27/2017.*/

internal interface AuthApi {

    @GET("token")
    fun getCredentials(@Query("scope") scope: String,
                       @Query("client_id") clientId: String,
                       @Query("client_secret") clientSecret: String,
                       @Query("2fa_supported") twoFaSupported: String,
                       @Query("lang") lang: String,
                       @Query("grant_type") grantType: String,
                       @Query("libverify_support") libverifySupport: String,
                       @Query("username") username: String,
                       @Query("password") password: String,
                       @Query("captcha_sid") captcha_sid: String?,
                       @Query("captcha_key") captcha: String?,
                       @Query("code") code: String?): Single<Credentials>

    @GET("auth.validatePhone")
    fun validatePhone(@Query("v") v: String,
                      @Query("lang") lang: String,
                      @Query("https") https: String,
                      @Query("sid") sid: String,
                      @Query("api_id") api_id: String): Completable
}