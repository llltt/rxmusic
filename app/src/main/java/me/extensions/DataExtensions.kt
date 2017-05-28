package me.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import okhttp3.FormBody
import okhttp3.Request
import okio.Buffer
import java.io.IOException


/** Created by Maksim Sukhotski on 4/9/2017. */
fun String.toMd5(): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    val md5sum = md.digest(this.toByteArray())
    return String.format("%032X", java.math.BigInteger(1, md5sum)).toLowerCase()
}

fun Int.toDp(r: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), r.displayMetrics)

fun Int.toPx(r: Resources): Int =
        Math.round(this * (r.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Int.closestFrom(list: MutableList<Int>): Int {
    list.forEach { if (this <= it) return it }
    return list.last()
}

fun Int.toSp(r: Resources): Float = this / r.displayMetrics.scaledDensity

val Any?.isNotNull: Boolean get() = this != null

fun Request.toRaw(): String {
    try {
        val buffer = Buffer()
        this.newBuilder().build().body()?.writeTo(buffer)
        return buffer.readUtf8()
    } catch (e: IOException) {
        return "did not work"
    }
}

fun FormBody.toRaw(): String {
    try {
        val buffer = Buffer()
        this.writeTo(buffer)
        return buffer.readUtf8()
    } catch (e: IOException) {
        return "did not work"
    }
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