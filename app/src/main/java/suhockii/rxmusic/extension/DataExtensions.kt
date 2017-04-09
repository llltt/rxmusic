package suhockii.rxmusic.extension

import java.math.BigInteger
import java.security.MessageDigest

/** Created by Maksim Sukhotski on 4/9/2017. */
fun md5(s: String): String {
    val input = "168"
    val md = MessageDigest.getInstance("MD5")
    val md5sum = md.digest(input.toByteArray())
    return String.format("%032X", BigInteger(1, md5sum))
}