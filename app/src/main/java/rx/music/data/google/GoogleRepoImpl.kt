package rx.music.data.google

import io.reactivex.Single
import rx.music.BuildConfig.*
import rx.music.dagger.Dagger
import rx.music.net.BaseFields.Companion.C2DM_API
import rx.music.net.BaseFields.Companion.GOOGLE_API
import rx.music.net.BaseFields.Companion.SEARCH_TYPE
import rx.music.net.apis.GoogleApi
import rx.music.net.models.google.CustomSearch
import rx.music.net.models.google.GcmToken
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/3/2017. */

class GoogleRepoImpl : GoogleRepo {
    @Inject lateinit var googleApi: GoogleApi

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch> =
            googleApi.getPicture(GOOGLE_API, q + " album cover", num, imgSize, SEARCH_TYPE,
                    GOOGLE_KEY, GOOGLE_CX)

    override fun register(): Single<GcmToken> =
            googleApi.register(C2DM_API, C2DM_APP, C2DM_SENDER, C2DM_DEVICE)
}