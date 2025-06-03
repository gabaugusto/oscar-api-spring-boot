package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.data.models.IndicacaoOscar
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel


@Composable
fun AtualizarScreen(viewModel: OscarViewModel = viewModel()) {
    val indicacoes by viewModel.indicacoes.collectAsState()
    var filmeSelecionado by remember { mutableStateOf<IndicacaoOscar?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Lista de filmes para seleção
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(indicacoes) { indicacao: IndicacaoOscar ->
                Card(
                    onClick = { filmeSelecionado = indicacao },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    border = if (filmeSelecionado?.id == indicacao.id) {
                        BorderStroke(2.dp, Color.Blue)
                    } else null
                ) {
                    Text(indicacao.nomeDoFilme, modifier = Modifier.padding(16.dp))
                }
            }
        }

        // Formulário de atualização
        filmeSelecionado?.let { filme ->
            var novoNomeDoFilme by remember { mutableStateOf(filme.nomeDoFilme) }
            var novoNomeDoIndicado by remember { mutableStateOf(filme.nomeDoIndicado) }

            OutlinedTextField(
                value = novoNomeDoFilme,
                onValueChange = { novoNomeDoIndicado = it },
                label = { Text("Novo Nome do Filme") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = novoNomeDoFilme,
                onValueChange = { novoNomeDoIndicado = it },
                label = { Text("Novo Nome Indicado") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.atualizarIndicacao(filme.id.toString(), novoNomeDoFilme)
                    filmeSelecionado = null
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Atualizar")
            }
        }
    }
}