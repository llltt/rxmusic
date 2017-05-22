package rx.music.net.models.auth

import com.google.gson.annotations.SerializedName

/** Created by Maksim Sukhotski on 3/27/2017.*/
class Credentials(
        var secret: String,
        @SerializedName(value = "access_token", alternate = arrayOf("token")) var token: String,
        @SerializedName("expires_in") val expiresIn: Long,
        @SerializedName("user_id") val userId: Long,
        @SerializedName("trusted_hash") val trustedHash: String)