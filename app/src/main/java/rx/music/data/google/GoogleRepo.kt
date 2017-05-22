package rx.music.data.google

import io.reactivex.Single
import okhttp3.ResponseBody
import rx.music.net.models.google.CustomSearch

/** Created by Maksim Sukhotski on 5/3/2017. */

interface GoogleRepo {
    fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch>
    fun register(): Single<ResponseBody>
}
