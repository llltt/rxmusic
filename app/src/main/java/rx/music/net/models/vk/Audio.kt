package rx.music.net.models.vk

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class Audio(@PrimaryKey var id: Long = 0,
                 @SerializedName("owner_id") var ownerId: Long = 0,
                 @SerializedName("is_licensed") var isLicensed: Boolean = false,
                 @SerializedName("access_key") var accessKey: String = "",
                 var album: Album = Album(),
                 var googlePhoto: GooglePhoto = GooglePhoto(),
                 var artist: String = "",
                 var title: String = "",
                 var duration: Int = 0,
                 var date: Int = 0,
                 var url: String = "",
                 var pos: Int = 0) : RealmObject()

@RealmClass
open class Album(@PrimaryKey var id: Long = 0,
                 @SerializedName("owner_id") var ownerId: Long = 0,
                 var thumb: Photo = Photo(),
                 var title: String = "") : RealmObject()