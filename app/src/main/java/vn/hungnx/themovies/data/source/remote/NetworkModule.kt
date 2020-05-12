package vn.hungnx.themovies.data.source.remote

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    const val TIMEOUT = 10

    fun createOkHttpCache(context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    fun createLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }

    fun createHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", "2cdf3a5c7cf412421485f89ace91e373")
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
//            .header("Content-Type", "application/json")
//            .header("X-App-Secret", "1234567890")
//            .header("Authorization", userRepositoryImpl.getAccessToken())
                .method(request.method, request.body)
                .build()
            chain.proceed(newRequest)
        }

    fun createOkHttpClient(
//    cache: Cache,
        logging: Interceptor,
        header: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
//        .cache(cache)
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(header)
            .addInterceptor(logging)
            .build()

    fun createAppRetrofit(
        okHttpClient: OkHttpClient
//    rxErrorHandlingFactory: RxErrorHandlingFactory,
    ): Retrofit =
        Retrofit.Builder()
//        .addCallAdapterFactory(rxErrorHandlingFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/")
            .client(okHttpClient)
            .build()


    fun createApiService(retrofit: Retrofit): ApiService =retrofit.create(ApiService::class.java)
}