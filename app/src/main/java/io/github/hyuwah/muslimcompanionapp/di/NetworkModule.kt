package io.github.hyuwah.muslimcompanionapp.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.github.hyuwah.muslimcompanionapp.BuildConfig
import io.github.hyuwah.muslimcompanionapp.data.remote.AlQuranCloudApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BaseUrl {
    const val AlQuranCloudApi = "https://api.alquran.cloud/"
}

val networkModule = module {

    single { ChuckerInterceptor(androidApplication()) }
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }

    single {
        createOkHttpClient(
                get<ChuckerInterceptor>(),
                get<HttpLoggingInterceptor>()
        )
    }

    single { createRestAdapter<AlQuranCloudApi>(get(), BaseUrl.AlQuranCloudApi) }

}

fun createOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    for (intercept in interceptors) {
                        addInterceptor(intercept)
                    }
                }
            }
            .build()
}

inline fun <reified T> createRestAdapter(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    return retrofit.create(T::class.java)
}