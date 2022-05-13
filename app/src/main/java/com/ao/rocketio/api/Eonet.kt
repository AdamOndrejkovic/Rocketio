package com.ao.rocketio.api

import com.ao.rocketio.data.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//The Earth Observatory Natural Event Tracker
interface Eonet {

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET("/api/v2.1/events")
    suspend fun getData(): Response<Data>

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET("/api/v2.1/categories/8")
    suspend fun getWildfires(): Response<Data>

    //Headers neccessary for CORS
    @Headers("Accept: application/json")
    @GET("/api/v2.1/categories/12")
    suspend fun getVolcanos(): Response<Data>
}