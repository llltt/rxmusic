package rx.music.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.music.BuildConfig
import rx.music.net.BaseFields.Companion.VK_API
import rx.music.net.deserializers.MusicPageDeserializer
import rx.music.net.interceptors.GcmInterceptor
import rx.music.net.interceptors.SecretsInterceptor
import rx.music.net.models.auth.Credentials
import rx.music.net.models.base.Response
import rx.music.net.models.vk.MusicPage


/** Created by Maksim Sukhotski on 4/9/2017. */
object Retrofit {
    fun <T> build(service: Class<T>, credentials: Credentials? = null): T =
            build(credentials).create(service)

    fun build(credentials: Credentials?): Retrofit =
            Retrofit.Builder()
                    .baseUrl(VK_API)
                    .addConverterFactory(createGsonConverter())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createClient(credentials))
                    .build()

    private fun createClient(credentials: Credentials?): OkHttpClient =
            with(OkHttpClient.Builder()) {
                if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor())
                if (credentials != null) addInterceptor(SecretsInterceptor())
                addInterceptor(GcmInterceptor())
                return build()
            }

    private fun createGsonConverter(): Converter.Factory =
            GsonConverterFactory.create(GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Response<MusicPage>().javaClass, MusicPageDeserializer())
                    .create())
}