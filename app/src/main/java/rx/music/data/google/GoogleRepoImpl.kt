package rx.music.data.google

import io.reactivex.Single
import rx.music.BuildConfig.*
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.BaseFields.Companion.GCM_API
import rx.music.net.BaseFields.Companion.GOOGLE_API
import rx.music.net.BaseFields.Companion.SEARCH_TYPE
import rx.music.net.apis.GoogleApi
import rx.music.net.models.google.CustomSearch
import rx.music.net.models.google.GcmToken
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/3/2017. */

class GoogleRepoImpl : GoogleRepo {
    @Inject lateinit var googleApi: GoogleApi
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun getPicture(q: String, num: Int, imgSize: String): Single<CustomSearch> =
            googleApi.getPicture(GOOGLE_API, q + " album cover", num, imgSize, SEARCH_TYPE,
                    GOOGLE_KEY, GOOGLE_CX)

    override fun register(): Single<GcmToken> = googleApi.register(GCM_API, GCM_X_subtype,
            GCM_X_X_subscription, GCM_X_X_subtype, GCM_X_app_ver, GCM_X_kid, GCM_X_osv, GCM_X_sig,
            GCM_X_cliv, GCM_X_gmsv, preferencesRepo.gcmXpub2, GCM_X_X_kid, GCM_X_appid, GCM_X_scope,
            GCM_X_subscription, GCM_X_app_ver_name, GCM_app, GCM_sender, GCM_device, GCM_cert,
            GCM_app_ver, GCM_info, GCM_gcm_ver)
}