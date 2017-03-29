package suhockii.rxmusic.data.api

import android.content.Context

/** Created by Maksim Sukhotski on 3/28/2017.*/
class SharedPreferences(context: Context) {
    companion object {
        const val PREFS_NAME = "PREFS_NAME"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val SECRET = "SECRET"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var access_token: String
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var secret: String
        get() = prefs.getString(SECRET, null)
        set(value) = prefs.edit().putString(SECRET, value).apply()

//    var city: City?
//        get() {
//            try {
//                return Gson().fromJson(prefs.getString(USER_CITY_KEY, ""), City::class.java)
//            } catch (e: JsonSyntaxException) {
//                return null
//            }
//        }
//        set(value) = prefs.edit().putString(USER_CITY_KEY, Gson().toJson(value)).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }
}