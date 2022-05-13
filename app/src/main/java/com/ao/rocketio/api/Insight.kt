package com.ao.rocketio.api

import com.ao.rocketio.data.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//Mars Weather Service API
interface Insight {

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET("/insight_weather/?api_key=pUg9PU3oIvkfEER8k0ZOF3bxiH1jumOvwEvY2YoL&feedtype=json&ver=1.0")
    suspend fun getData(): Response<Data>
}