package io.github.hyuwah.muslimcompanionapp.domain

import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import retrofit2.Response

interface AlQuranCloudRepository {

    suspend fun getAyah(id: Int): Response<AyahResponse>

    suspend fun getAyatByEdition(id: Int, edition: String): Response<AyahResponse>

}