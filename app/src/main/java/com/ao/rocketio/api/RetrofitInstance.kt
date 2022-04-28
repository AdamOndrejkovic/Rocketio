package com.ao.rocketio.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //By lazy we delay the init to when we first access the api
    val eonet: Eonet by lazy {
        Retrofit.Builder()
            .baseUrl("https://eonet.gsfc.nasa.gov")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Eonet::class.java)
    }
}