package io.github.hyuwah.muslimcompanionapp.data

import io.github.hyuwah.muslimcompanionapp.data.remote.AlQuranCloudApi
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.domain.MuslimSalatRepository
import retrofit2.Response

class RepositoryImpl(
        val alQuranCloudApi: AlQuranCloudApi
) : AlQuranCloudRepository, MuslimSalatRepository {

    override suspend fun getAyah(id: Int): Response<AyahResponse> {
        return alQuranCloudApi.getAyah(id)
    }

    override suspend fun getAyatByEdition(id: Int, edition: String): Response<AyahResponse> {
        return alQuranCloudApi.getAyahByEdition(id, edition)
    }

}