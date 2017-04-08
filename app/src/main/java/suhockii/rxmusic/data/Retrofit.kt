//package suhockii.rxmusic.data
//
//import com.facebook.stetho.okhttp3.StethoInterceptor
//import okhttp3.OkHttpClient
//import org.jetbrains.annotations.NotNull
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//
///** Created by Maksim Sukhotski on 4/8/2017. */
//internal object Retrofit {
//
//    private const val URL = "http://golos.ishukshin.ru/api/v1/"
//
//    fun build(): Retrofit {
//        val retrofit = Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(createHttpClient())
//                .build()
//
//        return retrofit
//
//    }
//
//    fun <T> build(service: Class<T>): T {
//        return build().create(service)
//    }
//
//    private fun createHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//                .addInterceptor(HeaderInterceptor())
//                .addNetworkInterceptor(StethoInterceptor())
//                .addInterceptor(createHttpLoggingInterceptor())
//                .build()
//    }
//
//
//    @NotNull
//    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        return httpLoggingInterceptor
//    }
//}
