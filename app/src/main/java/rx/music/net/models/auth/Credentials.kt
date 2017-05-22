package rx.music.net.models.auth

import com.google.gson.annotations.SerializedName

/** Created by Maksim Sukhotski on 3/27/2017.*/
class Credentials(
        val secret: String,
        @SerializedName(value = "access_token", alternate = arrayOf("token")) val token: String,
        @SerializedName("expires_in") val expiresIn: Long,
        @SerializedName("user_id") val userId: Long,
        @SerializedName("trusted_hash") val trustedHash: String)