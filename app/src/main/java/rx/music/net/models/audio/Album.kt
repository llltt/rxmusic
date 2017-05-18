package rx.music.net.models.audio

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class Album(@PrimaryKey var id: Long = 0,
                 @SerializedName("owner_id") var ownerId: Long = 0,
                 var thumb: Photo = Photo(),
                 var title: String = "") : RealmObject()