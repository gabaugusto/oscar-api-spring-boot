package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.components.IndicacaoItem
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListaIndicacoesScreen(viewModel: OscarViewModel = viewModel()) {
    val indicacoes by viewModel.indicacoes.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)
    val filtroAplicado = viewModel.filtroAplicado
    var showFilters by remember { mutableStateOf(false) }

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

    Column {
        IconButton(onClick = { showFilters = true }) {
            Icon(Icons.Default.Search, contentDescription = "Filtrar")
            if (filtroAplicado) {
                Badge(modifier = Modifier.offset((-8).dp, 8.dp)) {
                    Text("!")
            }
        }
    } }

    LazyColumn {
        items(indicacoes) { indicacao ->
            IndicacaoItem(indicacao = indicacao)
        }
    }
}