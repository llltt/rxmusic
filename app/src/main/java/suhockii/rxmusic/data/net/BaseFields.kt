package suhockii.rxmusic.data.net

import java.util.*

/** Created by Maksim Sukhotski on 4/9/2017. */
class BaseFields {
    companion object {
        const val V = "5.63"
        const val SCOPE = "nohttps,all"
        const val TWO_FA_SUPPORTED = "1"
        val LANG = Locale.getDefault().language
        const val GRANT_TYPE = "password"
        const val LIBVERIFY_SUPPORT = "1"
        const val HTTPS = "1"
    }
}