package com.ao.rocketio.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //By lazy we delay the init to when we first access the api

    //Retrofit instance for eonet api to retreive data
    val eonet: Eonet by lazy {
        Retrofit.Builder()
            .baseUrl("https://eonet.gsfc.nasa.gov")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Eonet::class.java)
    }

    //Retrofit instance for apod api to retreive data
    val apod: Apod by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apod::class.java)
    }

    //Retrofit instance for insight api to retreive data
    val insight: Insight by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.maas2.apollorion.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Insight::class.java)
    }
}