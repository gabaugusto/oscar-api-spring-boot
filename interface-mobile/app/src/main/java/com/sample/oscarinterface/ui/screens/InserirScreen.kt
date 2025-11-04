package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

/**
 * Tela para inserir uma nova indicação ao Oscar.
 * 
 * IMPORTANTE: O usuário deve informar pelo menos o idRegistro e o nomeFilme.
 * Os outros campos têm valores padrão, mas podem ser expandidos no futuro.
 */
@Composable
fun InserirScreen(viewModel: OscarViewModel = viewModel()) {
    // Estados locais para os campos do formulário
    var idRegistro by remember { mutableStateOf("") }
    var nomeFilme by remember { mutableStateOf("") }
    
    // Observa o estado de loading e erro do ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Limpa os campos quando a inserção é bem-sucedida (quando isLoading volta a false e não há erro)
    LaunchedEffect(isLoading, error) {
        if (!isLoading && error == null && idRegistro.isNotBlank()) {
            idRegistro = ""
            nomeFilme = ""
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo para ID do Registro (obrigatório)
        OutlinedTextField(
            value = idRegistro,
            onValueChange = { idRegistro = it },
            label = { Text("ID do Registro *") },
            placeholder = { Text("Ex: 123456") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Nome do Filme (obrigatório)
        OutlinedTextField(
            value = nomeFilme,
            onValueChange = { nomeFilme = it },
            label = { Text("Nome do Filme *") },
            placeholder = { Text("Ex: O Poderoso Chefão") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        // Exibe mensagem de erro, se houver
        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Botão para salvar
        // Nota: O botão só está habilitado se ambos os campos estiverem preenchidos
        Button(
            onClick = { 
                viewModel.inserirIndicacao(
                    idRegistro = idRegistro,
                    nomeFilme = nomeFilme
                )
            },
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(),
            enabled = idRegistro.isNotBlank() && nomeFilme.isNotBlank() && !isLoading
        ) {
            // Mostra loading no botão enquanto está salvando
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            } else {
                Text("Salvar")
            }
        }
    }
}