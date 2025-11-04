package com.example.projeto.controller

import com.example.projeto.model.IndicacaoOscar
import com.example.projeto.repository.IndicacaoOscarRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/indicacoes")
class OscarController(
    private val indicacaoOscarRepository: IndicacaoOscarRepository
) {

    // CRUD
    // CREATE, READ, UPDATE   , DELETE
    // POST  , GET , PUT/PATCH, DELETE

    @GetMapping
    fun getAllIndicacoes(): List<IndicacaoOscar> =
        indicacaoOscarRepository.findAll()
    // Exemplo de endpoint para pegar todos
    // api/indicacoes

    @GetMapping("/{id}")
    fun getIndicacaoById(@PathVariable id: String): ResponseEntity<IndicacaoOscar> {
        return try {
            val objectId = org.bson.types.ObjectId(id)
            indicacaoOscarRepository.findById(objectId)
                .map { indicacao -> ResponseEntity.ok(indicacao) }
                .orElseGet { ResponseEntity.notFound().build() }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
    // Exemplo de endpoint para pegar por ID
    // api/indicacoes/{id}

    @GetMapping("/registro/{idRegistro}")
    fun getIndicacoesByIdRegistro(@PathVariable idRegistro: Int): List<IndicacaoOscar> =
        indicacaoOscarRepository.findByIdRegistro(idRegistro)

    @GetMapping("/ano/{ano}")
    fun getIndicacoesByAno(@PathVariable ano: Int): List<IndicacaoOscar> =
        indicacaoOscarRepository.findByAnoCerimonia(ano)

    @GetMapping("/vencedores/{vencedor}")
    fun getIndicacoesByVencedor(@PathVariable vencedor: Boolean): List<IndicacaoOscar> =
        indicacaoOscarRepository.findByVencedor(vencedor)

    @GetMapping("/indicado/{nome}")
    fun getIndicacoesByNomeIndicado(@PathVariable nome: String): List<IndicacaoOscar> =
        indicacaoOscarRepository.findByNomeDoIndicadoContainingIgnoreCase(nome)

    @GetMapping("/filme/{nomeFilme}")
    fun getIndicacoesByNomeFilme(@PathVariable nomeFilme: String): List<IndicacaoOscar> =
        indicacaoOscarRepository.findByNomeDoFilmeContainingIgnoreCase(nomeFilme)

    @GetMapping("/categoria/{categoria}")
    fun getIndicacoesByCategoria(
        @PathVariable categoria: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<IndicacaoOscar> {
        return indicacaoOscarRepository.findByCategoria(
            categoria,
            PageRequest.of(page, size, Sort.by("nomeDoIndicado"))
        )
    }

    // Exemplo de endpoint para atualizar uma indicação
    @PostMapping
    fun createIndicacao(@RequestBody indicacao: IndicacaoOscar): IndicacaoOscar =
        indicacaoOscarRepository.save(indicacao)
    // Exemplo de body para criar
    // {
    //     "idRegistro": 9991110,
    //     "anoFilmagem": 2022,
    //     "anoCerimonia": 2023,
    //     "cerimonia": 95,
    //     "categoria": "Melhor Filme",
    //     "nomeDoIndicado": "Filme Exemplo",
    //     "nomeDoFilme": "Filme Exemplo",
    //     "vencedor": false
    // }
    // Exemplo de endpoint para criar
    // api/indicacoes

    @PutMapping("/{idRegistro}")
    fun updateIndicacao(
        @PathVariable idRegistro: Int,
        @RequestBody indicacaoAtualizada: IndicacaoOscar
    ): ResponseEntity<IndicacaoOscar> {
        val indicacoesExistentes = indicacaoOscarRepository.findByIdRegistro(idRegistro)

        return if (indicacoesExistentes.isNotEmpty()) {
            val indicacaoExistente = indicacoesExistentes.first()
            val updatedIndicacao = indicacaoExistente.copy(
                idRegistro = indicacaoAtualizada.idRegistro,
                anoFilmagem = indicacaoAtualizada.anoFilmagem,
                anoCerimonia = indicacaoAtualizada.anoCerimonia,
                cerimonia = indicacaoAtualizada.cerimonia,
                categoria = indicacaoAtualizada.categoria,
                nomeDoIndicado = indicacaoAtualizada.nomeDoIndicado,
                nomeDoFilme = indicacaoAtualizada.nomeDoFilme,
                vencedor = indicacaoAtualizada.vencedor
            )
            ResponseEntity.ok(indicacaoOscarRepository.save(updatedIndicacao))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    // {
    //     "idRegistro": 9991110,
    //     "anoFilmagem": 2022,
    //     "anoCerimonia": 2023,
    //     "cerimonia": 95,
    //     "categoria": "Melhor Filme Atualizado",
    //     "nomeDoIndicado": "Filme Exemplo Atualizado",
    //     "nomeDoFilme": "Filme Exemplo Atualizado",
    //     "vencedor": false
    // }

    @PatchMapping("/{idRegistro}/vencedor")
    fun updateVencedor(
        @PathVariable idRegistro: Int,
        @RequestParam vencedor: Boolean
    ): ResponseEntity<IndicacaoOscar> {
        val indicacoes = indicacaoOscarRepository.findByIdRegistro(idRegistro)
        return if (indicacoes.isNotEmpty()) {
            val indicacao = indicacoes.first()
            val updated = indicacao.copy(vencedor = vencedor)
            ResponseEntity.ok(indicacaoOscarRepository.save(updated))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    // Exemplo de endpoint para corrigir
    // api/indicacoes/{id}/vencedor?vencedor=true

    @DeleteMapping("registro/{idRegistro}")
    fun deleteIndicacao(@PathVariable idRegistro: Int): ResponseEntity<Void> {
        val indicacoes = indicacaoOscarRepository.findByIdRegistro(idRegistro)
        return if (indicacoes.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            indicacoes.forEach { indicacaoOscarRepository.delete(it) }
            ResponseEntity.noContent().build()
        }
    }
    // Exemplo de endpoint para apagar
    // api/indicacoes/registro/{idRegistro}
}