package suhockii.rxmusic.data.repositories.preferences

import suhockii.rxmusic.data.net.models.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepository {

    var credentials: Credentials
    var empty: Boolean

    fun clear()
}
