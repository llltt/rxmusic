package rx.music.net.models.vk

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

open class MusicPage(var owner: User?, var playlists: PlayLists?, var audios: Audios?)

@RealmClass
open class PlayLists(@PrimaryKey var id: Long? = 0,
                     var count: Int? = 0,
                     var items: RealmList<Playlist>? = RealmList()) : RealmObject()

@RealmClass
open class Audios(@PrimaryKey var id: Long = 0,
                  var count: Int = 0,
                  var items: RealmList<Audio> = RealmList(),
                  var profiles: RealmList<User> = RealmList()) : RealmObject()