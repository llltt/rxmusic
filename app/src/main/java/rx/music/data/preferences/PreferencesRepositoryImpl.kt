package rx.music.data.preferences

import android.content.Context
import com.google.gson.Gson
import rx.music.network.models.Credentials

/** Created by Maksim Sukhotski on 3/30/2017.*/
class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    companion object {
        const val PREFS_NAME = "PREFS_NAME"
        const val KEY_CREDENTIALS = "KEY_CREDENTIALS"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var isNotEmpty: Boolean
        get() = prefs.getString(KEY_CREDENTIALS, "").isNotEmpty()
        set(value) {}

    override var credentials: Credentials
        get() = Gson().fromJson(prefs.getString(KEY_CREDENTIALS, ""), Credentials::class.java)
        set(value) = prefs.edit().putString(KEY_CREDENTIALS, Gson().toJson(value)).apply()

    override fun clear() {
        prefs.edit().clear().apply()
    }
}