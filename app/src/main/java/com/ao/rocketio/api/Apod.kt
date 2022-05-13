package com.ao.rocketio.api

import com.ao.rocketio.data.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//Astronomy Picture of the Day
interface Apod {
    @GET("/planetary/apod?api_key=pUg9PU3oIvkfEER8k0ZOF3bxiH1jumOvwEvY2YoL")
    suspend fun getData(): Response<Image>
}