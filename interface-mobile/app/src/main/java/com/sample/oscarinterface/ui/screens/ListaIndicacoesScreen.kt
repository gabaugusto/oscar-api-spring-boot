package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.components.IndicacaoItem
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sample.oscarinterface.data.models.IndicacaoOscar

@Composable
fun ListaIndicacoesScreen(viewModel: OscarViewModel = viewModel()) {
    val indicacoes by viewModel.indicacoes.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)

    Text(text = "Lista de Indicações")

    LaunchedEffect(Unit) {
        viewModel.fetchIndicacoes()
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
    }

    error?.let {
        Text(
            text = it,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }

    LazyColumn {
        items(indicacoes) { indicacao ->
            IndicacaoItem(indicacao = indicacao)
        }
    }
}