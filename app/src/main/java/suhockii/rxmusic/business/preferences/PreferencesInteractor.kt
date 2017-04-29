package suhockii.rxmusic.business.preferences

import suhockii.rxmusic.data.net.models.Credentials

/** Created by Maksim Sukhotski on 4/8/2017. */
interface PreferencesInteractor {

    fun saveCredentials(credentials: Credentials)
    fun getCredentials(): Credentials
    fun authTokenEmpty(): Boolean
}