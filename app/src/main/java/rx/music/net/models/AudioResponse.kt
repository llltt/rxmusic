package rx.music.net.models

import rx.music.data.realm.models.Audio

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioResponse(val count: Int,
                    val items: MutableList<Audio>)