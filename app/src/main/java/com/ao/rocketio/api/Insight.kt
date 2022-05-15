package com.ao.rocketio.api

import com.ao.rocketio.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//Mars Weather Service API
interface Insight {

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET(".")
    suspend fun getData(): Response<Weather>
}