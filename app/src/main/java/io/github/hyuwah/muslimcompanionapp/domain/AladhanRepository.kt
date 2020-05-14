package io.github.hyuwah.muslimcompanionapp.domain

import io.github.hyuwah.muslimcompanionapp.data.remote.model.PrayerTimeResponse
import retrofit2.Response

interface AladhanRepository {
    suspend fun getTodayPrayerTime(): Response<PrayerTimeResponse>
}