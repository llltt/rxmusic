package rx.music.network

import java.util.*

/** Created by Maksim Sukhotski on 4/9/2017. */
class BaseFields {
    companion object {
        val LANG = Locale.getDefault().language
        const val V = "5.63"
        const val SCOPE = "nohttps,all"
        const val TWO_FA_SUPPORTED = "1"
        const val GRANT_TYPE = "password"
        const val LIBVERIFY_SUPPORT = "1"
        const val HTTPS = "1"
        const val VK_API = "https://api.vk.com/method/"
        const val VK_VALIDATION_API = "https://oauth.vk.com/token/"
        const val GOOGLE_API = "https://www.googleapis.com/customsearch/v1"
        const val SEARCH_TYPE = "image"
        const val IMG_SIZE = "large"
    }
}