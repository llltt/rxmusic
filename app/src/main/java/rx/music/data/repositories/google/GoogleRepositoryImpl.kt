package rx.music.data.repositories.google

import io.reactivex.Single
import rx.music.App
import rx.music.BuildConfig.GOOGLE_CX
import rx.music.BuildConfig.GOOGLE_KEY
import rx.music.data.network.BaseFields.Companion.GOOGLE_API
import rx.music.data.network.BaseFields.Companion.SEARCH_TYPE
import rx.music.data.network.apis.GoogleApi
import rx.music.data.network.models.CustomSearch
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/3/2017. */

class GoogleRepositoryImpl : GoogleRepository {
    @Inject lateinit var googleApi: GoogleApi

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch> {
        return googleApi.getPicture(GOOGLE_API, q + " album cover", num, imgSize, SEARCH_TYPE,
                GOOGLE_KEY, GOOGLE_CX)
    }
}