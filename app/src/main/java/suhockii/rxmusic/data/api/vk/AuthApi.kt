package suhockii.rxmusic.data.api.vk

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/** Created by Maksim Sukhotski on 3/27/2017.*/

internal interface AuthApi {

    companion object {
        const val URL = "https://oauth.vk.com/"

        const val SCOPE = "nohttps,all"
        const val CLIENT_ID = "2274003"
        const val CLIENT_SECRET = "hHbZxrka2uZ6jB1inYsH"
        const val TWO_FA_SUPPORTED = "1"
        const val LANG = "ru"
        const val GRANT_TYPE = "password"
        const val LIBVERIFY_SUPPORT = "1"
    }

    @GET("token")
    fun token(@Query("scope") scope: String,
              @Query("client_id") clientId: String,
              @Query("client_secret") clientSecret: String,
              @Query("2fa_supported") twoFaSupported: String,
              @Query("lang") lang: String,
              @Query("grant_type") grantType: String,
              @Query("libverify_support") libverifySupport: String,
              @Query("username") username: String,
              @Query("password") password: String): Single<Any>
}