package com.sample.oscarinterface.data.api

import com.sample.oscarinterface.data.models.IndicacaoOscar
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body

interface OscarApiService {
    // GET: Busca todas as indicações
    @GET("api/indicacoes")
    suspend fun getIndicacoes(): List<IndicacaoOscar>

    // GET: Busca indicações por ano
    @GET("api/indicacoes/ano/{ano}")
    suspend fun getIndicacoesPorAno(@Path("ano") ano: Int): List<IndicacaoOscar>

    // GET: Busca uma indicação específica por idRegistro
    // Atenção: Este endpoint pode variar conforme sua API. Ajuste se necessário!
    @GET("api/indicacoes/registro/{idRegistro}")
    suspend fun getIndicacaoPorIdRegistro(@Path("idRegistro") idRegistro: String): IndicacaoOscar

    // GET: Filtro combinado
    @GET("api/indicacoes/filtrar")
    suspend fun filtrarIndicacoes(
        @Query("ano") ano: Int?,
        @Query("categoria") categoria: String?,
        @Query("vencedor") vencedor: Boolean?
    ): List<IndicacaoOscar>

    // POST: Cria uma nova indicação
    // Importante: O @Body indica que o objeto será enviado no corpo da requisição
    @POST("api/indicacoes")
    suspend fun criarIndicacao(@Body oscar: IndicacaoOscar): Response<IndicacaoOscar>

    // DELETE: Remove uma indicação pelo idRegistro
    // Dica: O @Path insere o valor na URL: /api/indicacoes/{idRegistro}
    @DELETE("api/indicacoes/{idRegistro}")
    suspend fun deletarIndicacao(@Path("idRegistro") idRegistro: String): Response<Void>

    // PUT: Atualiza uma indicação existente
    // Explicação: O idRegistro vai na URL, e o objeto atualizado vai no corpo
    @PUT("api/indicacoes/{idRegistro}")
    suspend fun atualizarIndicacao(
        @Path("idRegistro") idRegistro: String,
        @Body oscar: IndicacaoOscar
    ): Response<IndicacaoOscar>
}
