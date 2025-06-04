package com.sample.oscarinterface.data.api

import com.sample.oscarinterface.data.models.IndicacaoOscar
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OscarApiService {
    @GET("api/indicacoes")
    suspend fun getIndicacoes(): List<IndicacaoOscar>

    @GET("api/indicacoes/ano/{ano}")
    suspend fun getIndicacoesPorAno(@Path("ano") ano: Int): List<IndicacaoOscar>

    // Filtro combinado
    @GET("api/indicacoes/filtrar")
    suspend fun filtrarIndicacoes(
        @Query("ano") ano: Int?,
        @Query("categoria") categoria: String?,
        @Query("vencedor") vencedor: Boolean?
    ): List<IndicacaoOscar>

    fun criarIndicacao(oscar: IndicacaoOscar)
    fun deletarIndicacao(string: String)
    fun atualizarIndicacao(string: kotlin.String, oscar: com.sample.oscarinterface.data.models.IndicacaoOscar)
}
