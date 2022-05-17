package com.ao.rocketio.api

import com.ao.rocketio.BuildConfig.NASA_API_KEY
import com.ao.rocketio.data.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

//Astronomy Picture of the Day
interface Apod {

    //Api call to get apod data / using key from local properties
    @GET("/planetary/apod?api_key=${NASA_API_KEY}")
    suspend fun getData(): Response<Image>
}