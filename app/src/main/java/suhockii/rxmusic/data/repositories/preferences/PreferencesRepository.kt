package suhockii.rxmusic.data.repositories.preferences

/** Created by Maksim Sukhotski on 3/30/2017.*/
interface PreferencesRepository {

    companion object {
        lateinit var TOKEN: String internal set
        lateinit var SECRET: String internal set
    }

    var access_token: String
    var secret: String
    fun clear()
}
