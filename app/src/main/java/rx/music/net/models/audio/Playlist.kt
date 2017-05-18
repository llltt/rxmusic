package rx.music.net.models.audio

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class Playlist(@PrimaryKey var id: Long = 0,
                    @SerializedName("owner_id") var ownerId: Long = 0,
                    @SerializedName("is_following") var isFolowing: Boolean = false,
                    @SerializedName("create_time") var createTime: Int = 0,
                    @SerializedName("update_time") var updateTime: Int = 0,
                    var thumbs: RealmList<Photo> = RealmList(),
                    var type: Int = 0,
                    var title: String = "",
                    var description: String = "",
                    var count: Int = 0,
                    var followers: Int = 0,
                    var plays: Int = 0,
                    var photo: Photo = Photo()) : RealmObject()