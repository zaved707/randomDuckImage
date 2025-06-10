package com.zavedahmad.randomduck.api

import retrofit2.Response
import retrofit2.http.GET


interface DuckApi {
    @GET("random")
    suspend fun getLink(): Response<DuckModel>
}