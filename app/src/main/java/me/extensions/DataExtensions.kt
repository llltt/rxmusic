package me.extensions

import android.content.res.Resources
import android.util.TypedValue



/** Created by Maksim Sukhotski on 4/9/2017. */
fun String.toMd5(): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val md5sum = md.digest(this.toByteArray())
    return String.format("%032X", java.math.BigInteger(1, md5sum)).toLowerCase()
}

fun Int.toDp(r: Resources): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), r.displayMetrics)
}

fun Int.toSp(r: Resources): Float {
    return this / r.displayMetrics.scaledDensity
}

fun android.content.Context.isNetworkConnected() = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager).activeNetworkInfo != null

/**
 * hh:mm:ss
 */
fun Int.toTime(): String {

    val l = this.toLong()
    val hours = java.util.concurrent.TimeUnit.SECONDS.toHours(l)
    val minutes = java.util.concurrent.TimeUnit.SECONDS.toMinutes(l) - java.util.concurrent.TimeUnit.HOURS.toMinutes(hours)
    val seconds = java.util.concurrent.TimeUnit.SECONDS.toSeconds(l) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.SECONDS.toMinutes(l))

    if (hours > 0) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        return String.format("%01d:%02d", minutes, seconds)
    }
}