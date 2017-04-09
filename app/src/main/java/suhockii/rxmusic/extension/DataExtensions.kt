package suhockii.rxmusic.extension

import android.content.Context
import android.net.ConnectivityManager
import java.math.BigInteger
import java.security.MessageDigest

/** Created by Maksim Sukhotski on 4/9/2017. */
fun md5(s: String): String {
    val md = MessageDigest.getInstance("MD5")
    val md5sum = md.digest(s.toByteArray())
    return String.format("%032X", BigInteger(1, md5sum)).toLowerCase()
}

fun Context.isNetworkConnected() = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
