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
    // Essa classe gerencia a manipulação dos dados de indicações do Oscar
    // Mas esse gerenciamento é feito com o foco de exibição na interface do usuário
    
    // Estado: Lista de indicações para exibir na tela
    private val _indicacoes = MutableStateFlow<List<IndicacaoOscar>>(emptyList())
    val indicacoes: StateFlow<List<IndicacaoOscar>> = _indicacoes.asStateFlow()

    // Estado: Controla se está carregando dados (para mostrar loading)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado: Armazena mensagens de erro, se houver
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Estado: Indicação encontrada na busca (usado na SearchScreen)
    private val _indicacaoEncontrada = MutableStateFlow<IndicacaoOscar?>(null)
    val indicacaoEncontrada: StateFlow<IndicacaoOscar?> = _indicacaoEncontrada.asStateFlow()

    // Método para buscar todas as indicações
    // Importante: Este método é chamado sempre que precisamos recarregar a lista
    fun fetchIndicacoes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _indicacoes.value = RetrofitInstance.api.getIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao carregar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para buscar uma indicação específica por idRegistro
    // Lembrete: idRegistro é o identificador único usado na API
    fun buscarPorIdRegistro(idRegistro: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _indicacaoEncontrada.value = null
            try {
                val resultado = RetrofitInstance.api.getIndicacaoPorIdRegistro(idRegistro)
                _indicacaoEncontrada.value = resultado
            } catch (e: Exception) {
                _error.value = "Registro não encontrado: ${e.message}"
                _indicacaoEncontrada.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Filtro de indicações por ano, categoria e vencedor
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

    // Gera um ID aleatório para o objeto Id interno
    // Nota: Este ID é usado internamente, mas o idRegistro é o importante para a API
    private fun generateRandomId(): String {
        val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..12)
            .map { characters.random() }
            .joinToString("")
    }
    
    // Converte string para o formato Id esperado pelo modelo
    private fun String.toId(): Id {
        return Id(
            timestamp = System.currentTimeMillis(),
            date = this
        )
    }

    // Insere uma nova indicação
    // Atenção: Você pode expandir este método para aceitar todos os campos do formulário
    fun inserirIndicacao(
        idRegistro: String,
        nomeFilme: String,
        nomeIndicado: String = "Indicado Exemplo",
        categoria: String = "FILM",
        anoFilmagem: Int = 2023,
        anoCerimonia: Int = 2024,
        cerimonia: Int = 8,
        vencedor: Boolean = false
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val novaIndicacao = IndicacaoOscar(
                    id = generateRandomId().toId(),
                    idRegistro = idRegistro,
                    anoFilmagem = anoFilmagem,
                    anoCerimonia = anoCerimonia,
                    cerimonia = cerimonia,
                    categoria = categoria,
                    nomeDoFilme = nomeFilme,
                    nomeDoIndicado = nomeIndicado,
                    vencedor = vencedor
                )
                // A API retorna a indicação criada, mas não precisamos usar o retorno aqui
                RetrofitInstance.api.criarIndicacao(novaIndicacao)
                // Recarrega a lista para mostrar a nova indicação
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao inserir: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Apaga uma indicação usando o idRegistro
    // Importante: Usamos idRegistro, não o id interno!
    fun apagarIndicacao(idRegistro: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                RetrofitInstance.api.deletarIndicacao(idRegistro)
                // Recarrega a lista para remover a indicação deletada
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao apagar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Atualiza uma indicação existente
    // Explicação: Precisamos do idRegistro para identificar qual registro atualizar
    fun atualizarIndicacao(
        idRegistro: String,
        nomeFilme: String? = null,
        nomeIndicado: String? = null,
        categoria: String? = null,
        anoFilmagem: Int? = null,
        anoCerimonia: Int? = null,
        cerimonia: Int? = null,
        vencedor: Boolean? = null,
        indicacaoAtual: IndicacaoOscar // Precisamos dos dados atuais para manter o que não mudou
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // Cria um novo objeto mantendo os valores atuais e atualizando apenas o que foi informado
                val atualizacao = IndicacaoOscar(
                    id = indicacaoAtual.id, // Mantém o ID original
                    idRegistro = idRegistro,
                    anoFilmagem = anoFilmagem ?: indicacaoAtual.anoFilmagem,
                    anoCerimonia = anoCerimonia ?: indicacaoAtual.anoCerimonia,
                    cerimonia = cerimonia ?: indicacaoAtual.cerimonia,
                    categoria = categoria ?: indicacaoAtual.categoria,
                    nomeDoFilme = nomeFilme ?: indicacaoAtual.nomeDoFilme,
                    nomeDoIndicado = nomeIndicado ?: indicacaoAtual.nomeDoIndicado,
                    vencedor = vencedor ?: indicacaoAtual.vencedor
                )
                RetrofitInstance.api.atualizarIndicacao(idRegistro, atualizacao)
                // Recarrega a lista para mostrar as alterações
                fetchIndicacoes()
            } catch (e: Exception) {
                _error.value = "Erro ao atualizar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}