package rx.music.data.google

import io.reactivex.Single
import rx.music.net.models.audio.CustomSearch

/** Created by Maksim Sukhotski on 5/3/2017. */

interface GoogleRepo {
    fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch>
}
