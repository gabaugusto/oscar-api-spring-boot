package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

/**
 * Tela para apagar indicações ao Oscar.
 * 
 * IMPORTANTE: Usa o idRegistro para deletar, não o id interno.
 * A lista é carregada automaticamente ao entrar na tela.
 */
@Composable
fun ApagarScreen(viewModel: OscarViewModel = viewModel()) {
    val indicacoes by viewModel.indicacoes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Carrega as indicações quando a tela é aberta pela primeira vez
    LaunchedEffect(Unit) {
        if (indicacoes.isEmpty()) {
            viewModel.fetchIndicacoes()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Exibe mensagem de erro, se houver
        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Mostra loading enquanto carrega
        if (isLoading && indicacoes.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            // Lista de indicações com botão de deletar
            LazyColumn {
                items(indicacoes) { indicacao ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Informações da indicação
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = indicacao.nomeDoFilme,
                                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "ID Registro: ${indicacao.idRegistro}",
                                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Indicado: ${indicacao.nomeDoIndicado}",
                                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                                )
                            }
                            
                            // Botão de deletar
                            IconButton(
                                onClick = { 
                                    // Usa idRegistro para deletar, como solicitado
                                    viewModel.apagarIndicacao(indicacao.idRegistro) 
                                },
                                enabled = !isLoading
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Apagar",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
