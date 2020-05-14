package io.github.hyuwah.muslimcompanionapp.data.remote

import io.github.hyuwah.muslimcompanionapp.data.remote.model.PrayerTimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AladhanApi {

    @GET("timingsByCity?method=11")
    suspend fun getTodayPrayerTime(
            @Query("city") city: String,
            @Query("country") country: String
    ): Response<PrayerTimeResponse>

}