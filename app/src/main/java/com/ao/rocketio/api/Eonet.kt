package com.ao.rocketio.api

import com.ao.rocketio.data.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface Eonet {

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET("/api/v2.1/events")
    suspend fun getData(): Response<Data>
}