package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.components.IndicacaoItem
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

/**
 * Tela para buscar uma indicação específica por ID do Registro.
 * 
 * IMPORTANTE: O usuário deve informar o idRegistro para buscar.
 * Se encontrar, exibe os detalhes da indicação.
 */
@Composable
fun SearchScreen(viewModel: OscarViewModel = viewModel()) {
    // Estado local para o campo de busca
    var idRegistroBusca by remember { mutableStateOf("") }
    
    // Observa os estados do ViewModel
    val indicacaoEncontrada by viewModel.indicacaoEncontrada.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo para inserir o ID do Registro
        OutlinedTextField(
            value = idRegistroBusca,
            onValueChange = { idRegistroBusca = it },
            label = { Text("ID do Registro") },
            placeholder = { Text("Digite o ID do registro para buscar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        // Botão para buscar
        Button(
            onClick = { 
                if (idRegistroBusca.isNotBlank()) {
                    viewModel.buscarPorIdRegistro(idRegistroBusca)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = idRegistroBusca.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            } else {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe mensagem de erro, se houver
        error?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Exibe a indicação encontrada
        indicacaoEncontrada?.let { indicacao ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Registro encontrado:",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            IndicacaoItem(indicacao = indicacao)
        }
    }
}