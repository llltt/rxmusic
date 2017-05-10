package rx.music.net.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */
@RealmClass
open class Audio(@PrimaryKey var id: Long = 0,
                 var owner_id: Long = 0,
                 var artist: String = "",
                 var title: String = "",
                 var duration: Int = 0,
                 var date: String = "",
                 var url: String = "",
                 var lyrics_id: Long = 0,
                 var genre_id: Long = 0,
                 var pic: String? = "") : RealmObject()