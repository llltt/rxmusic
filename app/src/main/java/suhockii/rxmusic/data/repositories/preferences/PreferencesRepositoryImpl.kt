package suhockii.rxmusic.data.repositories.preferences

import android.content.Context
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository.Companion.SECRET
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository.Companion.TOKEN

/** Created by Maksim Sukhotski on 3/30/2017.*/
class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {

    companion object {
        const val PREFS_NAME = "PREFS_NAME"
        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val KEY_SECRET = "SECRET"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        TOKEN = accessToken
        SECRET = secret
    }

    override var accessToken: String
        get() = prefs.getString(KEY_ACCESS_TOKEN, "")
        set(value) = prefs.edit().putString(KEY_ACCESS_TOKEN, value).apply()

    override var secret: String
        get() = prefs.getString(KEY_SECRET, "")
        set(value) = prefs.edit().putString(KEY_SECRET, value).apply()


    override fun clear() {
        prefs.edit().clear().apply()
    }
}