package com.example.projeto.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "registros") // substitua pelo nome real da sua coleção
data class IndicacaoOscar(
    @Id
    val id: ObjectId? = null,

    @Field("id_registro")
    val idRegistro: Int,

    @Field("ano_filmagem")
    val anoFilmagem: Int,

    @Field("ano_cerimonia")
    val anoCerimonia: Int,

    @Field("cerimonia")
    val cerimonia: Int,

    @Field("categoria")
    val categoria: String,

    @Field("nome_do_indicado")
    val nomeDoIndicado: String,

    @Field("nome_do_filme")
    val nomeDoFilme: String,

    @Field("vencedor")
    val vencedor: Boolean
)