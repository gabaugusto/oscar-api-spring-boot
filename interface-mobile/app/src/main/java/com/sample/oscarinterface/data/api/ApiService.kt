package com.sample.oscarinterface.data.api

import com.sample.oscarinterface.data.models.IndicacaoOscar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface OscarApiService {
    @GET("api/indicacoes")
    suspend fun getIndicacoes(): List<IndicacaoOscar>

    @GET("api/indicacoes/ano/{ano}")
    suspend fun getIndicacoesPorAno(@Path("ano") ano: Int): List<IndicacaoOscar>
}
