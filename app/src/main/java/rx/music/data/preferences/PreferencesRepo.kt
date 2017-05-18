package rx.music.data.preferences

import rx.music.net.models.auth.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepo {
    var credentials: Credentials
    var isAuthorized: Boolean
    fun clear()
}
