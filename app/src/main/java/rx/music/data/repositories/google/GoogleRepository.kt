package rx.music.data.repositories.google

import io.reactivex.Single
import rx.music.data.network.models.CustomSearch

/** Created by Maksim Sukhotski on 5/3/2017. */

interface GoogleRepository {
    fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch>
}
