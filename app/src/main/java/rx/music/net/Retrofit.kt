package rx.music.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.music.BuildConfig
import rx.music.net.BaseFields.Companion.VK_API
import rx.music.net.interceptors.VkInterceptor
import rx.music.net.models.Credentials

/** Created by Maksim Sukhotski on 4/9/2017. */
object Retrofit {
    fun <T> build(service: Class<T>, credentials: Credentials? = null): T =
            build(credentials).create(service)

    fun build(credentials: Credentials?): Retrofit =
            Retrofit.Builder()
                    .baseUrl(VK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getClient(credentials))
                    .build()

    private fun getClient(credentials: Credentials?): OkHttpClient = with(OkHttpClient.Builder()) {
        if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor())
        if (credentials != null) addInterceptor(VkInterceptor(credentials))
        return build()
    }
}