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
import rx.music.net.deserializers.DynamicJsonDeserializer
import rx.music.net.interceptors.VkInterceptor
import rx.music.net.models.auth.Credentials
import rx.music.net.models.base.Response


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

    private fun createClient(credentials: Credentials?): OkHttpClient = with(OkHttpClient.Builder()) {
        if (BuildConfig.DEBUG) addNetworkInterceptor(StethoInterceptor())
        if (credentials != null) addInterceptor(VkInterceptor(credentials))
        return build()
    }

    private fun createGsonConverter(): Converter.Factory {
        return GsonConverterFactory
                .create(
                        GsonBuilder()
                                .setLenient()
                                .registerTypeAdapter(Response::class.java, DynamicJsonDeserializer())
//                        .registerTypeAdapter(GcmToken::class.java, EqualsJsonDeserializer())
                                .create())
    }
}