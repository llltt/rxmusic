package rx.music.net.models.audio

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class Photo(@PrimaryKey var id: Long = 0,
                 var photo_34: String? = "",
                 var photo_68: String? = "",
                 var photo_135: String? = "",
                 var photo_270: String? = "",
                 var photo_300: String? = "",
                 var photo_600: String? = "",
                 var width: Int? = 0,
                 var height: Int? = 0) : RealmObject()
