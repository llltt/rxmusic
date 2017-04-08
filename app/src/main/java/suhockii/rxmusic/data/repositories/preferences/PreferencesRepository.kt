package suhockii.rxmusic.data.repositories.preferences

import suhockii.rxmusic.data.repositories.auth.models.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepository {

    companion object {
        lateinit var TOKEN: String internal set
        lateinit var SECRET: String internal set
    }

    var credentials: Credentials
    fun clear()
}
