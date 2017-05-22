package rx.music.net.models.vk

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class Photo(@PrimaryKey var id: Long = 0,
                 @SerializedName("photo_34") var photo34: String? = "",
                 @SerializedName("photo_68") var photo68: String? = "",
                 @SerializedName("photo_135") var photo135: String? = "",
                 @SerializedName("photo_270") var photo270: String? = "",
                 @SerializedName("photo_300") var photo300: String? = "",
                 @SerializedName("photo_600") var photo600: String? = "",
                 var width: Int? = 0,
                 var height: Int? = 0) : RealmObject()
