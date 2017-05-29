package rx.music.net.models.vk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class GooglePhoto(@PrimaryKey var id: Long = 0,
                       var photo: String? = "") : RealmObject()
