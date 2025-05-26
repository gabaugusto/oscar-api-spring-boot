package com.sample.oscarinterface.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                print("Indicacoes fetched successfully: ${_indicacoes.value.size} items")
            } catch (e: Exception) {
                _error.value = "Erro ao carregar: ${e.message}"
                print("Erro ao carregar indicacoes: ${e.message}")
            } finally {
                _isLoading.value = false
                print("isLoading alterado para false")
            }
        }
    }
}