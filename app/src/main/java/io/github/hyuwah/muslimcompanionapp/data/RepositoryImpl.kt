package io.github.hyuwah.muslimcompanionapp.data

import io.github.hyuwah.muslimcompanionapp.data.remote.AlQuranCloudApi
import io.github.hyuwah.muslimcompanionapp.data.remote.AladhanApi
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.data.remote.model.PrayerTimeResponse
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.domain.AladhanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryImpl(
        private val alQuranCloudApi: AlQuranCloudApi,
        private val aladhanApi: AladhanApi
) : AlQuranCloudRepository, AladhanRepository {

    override suspend fun getAyah(id: Int): Response<AyahResponse> {
        return withContext(Dispatchers.IO) {
            alQuranCloudApi.getAyah(id)
        }
    }

    override suspend fun getAyatByEdition(id: Int, edition: String): Response<AyahResponse> {
        return withContext(Dispatchers.IO) {
            alQuranCloudApi.getAyahByEdition(id, edition)
        }
    }

    override suspend fun getTodayPrayerTime(): Response<PrayerTimeResponse> {
        return withContext(Dispatchers.IO) {
            aladhanApi.getTodayPrayerTime("Jakarta", "Indonesia")
        }
    }

}