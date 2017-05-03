package rx.music.data.repositories.preferences

import rx.music.data.network.models.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepository {
    var credentials: Credentials
    var empty: Boolean
    fun clear()
}
