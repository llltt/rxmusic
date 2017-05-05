package rx.music.data.google

import io.reactivex.Single
import rx.music.network.models.CustomSearch

/** Created by Maksim Sukhotski on 5/3/2017. */

interface GoogleRepository {
    fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch>
}
