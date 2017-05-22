package rx.music.net.models.vk

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/** Created by Maksim Sukhotski on 4/9/2017. */

@RealmClass
open class MusicPage(@PrimaryKey var id: Long = 0,
                     var owner: User = User(),
                     var playlists: PlayListItems? = PlayListItems(),
                     var audios: AudioItems? = AudioItems()) : RealmObject()

@RealmClass
open class PlayListItems(@PrimaryKey var id: Long? = 0,
                         var count: Int? = 0,
                         var items: RealmList<Playlist>? = RealmList()) : RealmObject()

@RealmClass
open class AudioItems(@PrimaryKey var id: Long = 0,
                      var count: Int = 0,
                      var items: RealmList<Audio> = RealmList()) : RealmObject()