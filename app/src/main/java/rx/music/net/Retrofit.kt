package rx.music.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.music.net.BaseFields.Companion.VK_API

/** Created by Maksim Sukhotski on 4/9/2017. */
object Retrofit {
    fun build(): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(VK_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build()
        return retrofit
    }

    fun <T> build(service: Class<T>): T {
        return build().create(service)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }
}