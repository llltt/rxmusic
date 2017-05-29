package rx.music.net.models.vk

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import rx.music.net.BaseFields.Companion.musicHeight

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
                 var height: Int? = 0) : RealmObject() {
    fun getSuitablePhoto(): String {
        when (musicHeight) {
            34 -> if (photo34?.isNotBlank() ?: false) return photo34 ?: ""
            68 -> if (photo68?.isNotBlank() ?: false) return photo68 ?: ""
            135 -> if (photo135?.isNotBlank() ?: false) return photo135 ?: ""
            270 -> if (photo270?.isNotBlank() ?: false) return photo270 ?: ""
            300 -> if (photo300?.isNotBlank() ?: false) return photo300 ?: ""
        }
        return photo600 ?: ""
    }
}
