package io.github.hyuwah.muslimcompanionapp.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.github.hyuwah.muslimcompanionapp.BuildConfig
import io.github.hyuwah.muslimcompanionapp.data.remote.AlQuranCloudApi
import io.github.hyuwah.muslimcompanionapp.data.remote.AladhanApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BaseUrl {
    const val AlQuranCloudApi = "https://api.alquran.cloud/"
    const val AladhanApi = "https://aladhan.p.rapidapi.com/"
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

    single(named("Aladhan")) {
        val headers = mutableMapOf(
                "x-rapidapi-host" to "aladhan.p.rapidapi.com",
                "x-rapidapi-key" to BuildConfig.RAPIDAPI_KEY
        )
        createOkHttpClient(
                get<ChuckerInterceptor>(),
                get<HttpLoggingInterceptor>(),
                headers = headers
        )
    }

    single { createRestAdapter<AlQuranCloudApi>(get(), BaseUrl.AlQuranCloudApi) }
    single { createRestAdapter<AladhanApi>(get(named("Aladhan")), BaseUrl.AladhanApi) }

}

fun createOkHttpClient(
        vararg interceptors: Interceptor,
        headers: Map<String, String> = emptyMap()
): OkHttpClient {
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    for (intercept in interceptors) {
                        addInterceptor(intercept)
                    }
                }
                if (headers.isNotEmpty()) {
                    addInterceptor { chain ->
                        val builder = chain.request().newBuilder()
                        headers.forEach { builder.addHeader(it.key, it.value) }
                        chain.proceed(builder.build())
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