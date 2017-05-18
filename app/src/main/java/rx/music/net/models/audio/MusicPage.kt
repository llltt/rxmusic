package rx.music.net.models.audio

import Items
import User

/** Created by Maksim Sukhotski on 4/9/2017. */
class MusicPage(val owner: User,
                val playlists: Items<MutableList<Playlist>>,
                val audios: Items<MutableList<Audio>>)