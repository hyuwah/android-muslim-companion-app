package io.github.hyuwah.muslimcompanionapp.data.remote

import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlQuranCloudApi {

    @GET("/ayah/{id}")
    suspend fun getAyah(@Path("id") id: Int): Response<AyahResponse>

    @GET("/ayah/{id}/{edition}")
    suspend fun getAyahByEdition(
            @Path("id") id: Int,
            @Path("edition") edition: String
    ): Response<AyahResponse>

}