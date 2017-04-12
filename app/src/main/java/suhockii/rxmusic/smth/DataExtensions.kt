package suhockii.rxmusic.smth

import android.content.Context
import android.net.ConnectivityManager
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

/** Created by Maksim Sukhotski on 4/9/2017. */
fun md5(s: String): String {
    val md = MessageDigest.getInstance("MD5")
    val md5sum = md.digest(s.toByteArray())
    return String.format("%032X", BigInteger(1, md5sum)).toLowerCase()
}

fun Context.isNetworkConnected() = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

/**
 * hh:mm:ss
 */
fun String.toTime(): String {

    val l = this.toLong()
    val hours = TimeUnit.SECONDS.toHours(l)
    val minutes = TimeUnit.SECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.SECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(l))

    if (hours > 0) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        return String.format("%01d:%02d", minutes, seconds)
    }
}