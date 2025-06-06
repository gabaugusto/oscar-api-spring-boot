package com.example.projeto.repository

import com.example.projeto.model.IndicacaoOscar
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IndicacaoOscarRepository : MongoRepository<IndicacaoOscar, String> {

    fun findByAnoCerimonia(ano: Int): List<IndicacaoOscar>

    // findByCategoria(categoria: String, pageable: SpringDataWebProperties.Pageable): Page<IndicacaoOscar>

    fun findByCategoria(categoria: String, pageable: PageRequest): Page<IndicacaoOscar>

    fun findByVencedor(vencedor: Boolean): List<IndicacaoOscar>

    fun findByIdRegistro(idRegistro: Int): List<IndicacaoOscar>

    fun findByNomeDoIndicadoContainingIgnoreCase(nome: String): List<IndicacaoOscar>

    fun findByNomeDoFilmeContainingIgnoreCase(filme: String): List<IndicacaoOscar>
}