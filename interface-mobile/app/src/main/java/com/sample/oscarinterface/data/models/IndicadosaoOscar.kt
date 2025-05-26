package com.sample.oscarinterface.data.models

data class IndicacaoOscar(
    val id: Id,
    val idRegistro: String,
    val anoFilmagem: Int,
    val anoCerimonia: Int,
    val cerimonia: Int,
    val categoria: String,
    val nomeDoIndicado: String,
    val nomeDoFilme: String,
    val vencedor: Boolean
)

data class Id(
    val timestamp: Long,
    val date: String
)