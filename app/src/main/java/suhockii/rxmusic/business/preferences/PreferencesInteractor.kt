package suhockii.rxmusic.business.preferences

/** Created by Maksim Sukhotski on 4/8/2017. */
interface PreferencesInteractor {

    fun saveCredentials(token: String, secret: String)
}