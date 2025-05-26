package com.sample.oscarinterface.data.repository

import com.sample.oscarinterface.data.api.OscarApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //BASE_URL = "http://localhost:8080/" // Altere localhost para o IP do seu servidor se estiver testando no emulador Android
    private const val BASE_URL = "http://10.0.2.2:8080/" // Altere para sua URL!

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OscarApiService by lazy {
        retrofit.create(OscarApiService::class.java)
    }
}