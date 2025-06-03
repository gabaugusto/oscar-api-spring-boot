package com.sample.oscarinterface.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.oscarinterface.data.models.Id
import com.sample.oscarinterface.data.models.IndicacaoOscar
import com.sample.oscarinterface.data.repository.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OscarViewModel : ViewModel() {
    private val _indicacoes = MutableStateFlow<List<IndicacaoOscar>>(emptyList())
    open val indicacoes: StateFlow<List<IndicacaoOscar>> = _indicacoes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchIndicacoes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _indicacoes.value = RetrofitInstance.api.getIndicacoes()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Filtro de indicações
    private val _filtroAplicado = mutableStateOf(false)
    val filtroAplicado: Boolean get() = _filtroAplicado.value

    fun filtrarIndicacoes(ano: Int?, categoria: String?, vencedor: Boolean?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _indicacoes.value = RetrofitInstance.api.filtrarIndicacoes(ano, categoria, vencedor)
                _filtroAplicado.value = true
            } catch (e: Exception) {
                _error.value = "Erro ao filtrar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparFiltros() {
        viewModelScope.launch {
            fetchIndicacoes()
            _filtroAplicado.value = false
        }
    }

    // Gerador de IDs aleatórios com pelo menos 12 caracteres alfanuméricos

    private fun generateRandomId(): String {
        val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..12)
            .map { characters.random() }
            .joinToString("")
    }
    // Converte o ID aleatório para o formato esperado
    private fun String.toId(): Id {
        return Id(
            timestamp = System.currentTimeMillis(),
            date = this
        )
    }

    // ID aleatório para uso nas operações de inserção e atualização
    private val randomId: String = generateRandomId()

    fun inserirIndicacao(nomeFilme: String) {
        viewModelScope.launch {
            try {
                val novaIndicacao = IndicacaoOscar(
                    id = randomId.toId(),
                    idRegistro = "789",
                    anoFilmagem = 2023,
                    anoCerimonia = 2024,
                    cerimonia = 8,
                    categoria = "FILM",
                    nomeDoFilme = nomeFilme,
                    nomeDoIndicado = "Indicado Exemplo",
                    vencedor = false
                )
                RetrofitInstance.api.criarIndicacao(novaIndicacao)
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao inserir: ${e.message}"
            }
        }
    }

    fun apagarIndicacao(id: String) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deletarIndicacao(id)
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao apagar: ${e.message}"
            }
        }
    }

    fun atualizarIndicacao(id: String?, novoNome: String) {
        if (id == null) return
        viewModelScope.launch {
            try {
                val atualizacao = IndicacaoOscar(
                    id = randomId.toId(),
                    idRegistro = "registro123",
                    anoFilmagem = 2023,
                    anoCerimonia = 2024,
                    cerimonia = 2,
                    categoria = "FILM",
                    nomeDoFilme = novoNome,
                    nomeDoIndicado = "Indicado Exemplo",
                    vencedor = false
                )
                RetrofitInstance.api.atualizarIndicacao(id.toString(), atualizacao)
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao atualizar: ${e.message}"
            }
        }
    }
}