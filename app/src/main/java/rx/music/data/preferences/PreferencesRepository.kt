package rx.music.data.preferences

import rx.music.net.models.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepository {
    var credentials: Credentials
    var isNotEmpty: Boolean
    fun clear()
}
