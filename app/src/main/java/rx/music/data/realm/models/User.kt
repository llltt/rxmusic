package rx.music.data.realm.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class User(@PrimaryKey var id: Long = 0,
                var audioList: RealmList<Audio> = RealmList(),
                @SerializedName("first_name") var firstName: String = "",
                @SerializedName("last_name") var lastName: String = "",
                @SerializedName("photo_max_orig") var photo: String = "",
                @SerializedName("can_see_audio") var readable: Int = 0,
                @SerializedName("music") var musicalPrefs: String = "") : RealmObject()