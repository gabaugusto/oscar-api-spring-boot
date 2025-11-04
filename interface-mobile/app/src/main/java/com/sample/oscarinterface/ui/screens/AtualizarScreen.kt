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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

/**
 * Tela para atualizar uma indicação existente.
 * 
 * FLUXO:
 * 1. O usuário informa o idRegistro
 * 2. Clica em "Buscar" para carregar os dados
 * 3. Edita os campos desejados
 * 4. Clica em "Atualizar" para salvar as alterações
 * 
 * IMPORTANTE: O idRegistro é necessário para identificar qual registro atualizar.
 */
@Composable
fun AtualizarScreen(viewModel: OscarViewModel = viewModel()) {
    // Estados locais
    var idRegistroBusca by remember { mutableStateOf("") }
    var idRegistroAtual by remember { mutableStateOf("") }
    var nomeFilme by remember { mutableStateOf("") }
    var nomeIndicado by remember { mutableStateOf("") }
    
    // Observa os estados do ViewModel
    val indicacaoEncontrada by viewModel.indicacaoEncontrada.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Quando encontrar uma indicação, preenche os campos
    indicacaoEncontrada?.let { indicacao ->
        if (nomeFilme.isEmpty()) {
            // Preenche apenas uma vez quando a indicação é encontrada
            idRegistroAtual = indicacao.idRegistro
            nomeFilme = indicacao.nomeDoFilme
            nomeIndicado = indicacao.nomeDoIndicado
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Passo 1: Buscar pelo idRegistro
        Text(
            text = "Passo 1: Informe o ID do Registro",
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = idRegistroBusca,
            onValueChange = { idRegistroBusca = it },
            label = { Text("ID do Registro") },
            placeholder = { Text("Ex: 123456") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && indicacaoEncontrada == null
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = { 
                if (idRegistroBusca.isNotBlank()) {
                    viewModel.buscarPorIdRegistro(idRegistroBusca)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = idRegistroBusca.isNotBlank() && !isLoading && indicacaoEncontrada == null
        ) {
            if (isLoading && indicacaoEncontrada == null) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            } else {
                Text("Buscar Registro")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exibe mensagem de erro, se houver
        error?.let { erroMsg ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = erroMsg,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Passo 2: Editar os campos (só aparece se encontrou o registro e ainda temos o idRegistroBusca)
        // Nota: Se limparmos o idRegistroBusca, não mostra mais o formulário mesmo se encontrada ainda tiver valor
        if (idRegistroBusca.isNotBlank() && indicacaoEncontrada != null) {
            val it = indicacaoEncontrada!!
            Text(
                text = "Passo 2: Edite os campos que deseja atualizar",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Nome do Filme
            OutlinedTextField(
                value = nomeFilme,
                onValueChange = { nomeFilme = it },
                label = { Text("Nome do Filme") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Nome do Indicado
            OutlinedTextField(
                value = nomeIndicado,
                onValueChange = { nomeIndicado = it },
                label = { Text("Nome do Indicado") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para atualizar
            Button(
                onClick = {
                    viewModel.atualizarIndicacao(
                        idRegistro = idRegistroAtual,
                        nomeFilme = nomeFilme,
                        nomeIndicado = nomeIndicado,
                        indicacaoAtual = it // Passa a indicação atual para manter os outros campos
                    )
                    // Limpa os campos após atualizar com sucesso
                    // (será feito quando isLoading voltar a false sem erro)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && (nomeFilme != it.nomeDoFilme || nomeIndicado != it.nomeDoIndicado)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                } else {
                    Text("Atualizar Registro")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para buscar outro registro
            Button(
                onClick = {
                    // Limpa tudo para buscar outro registro
                    idRegistroBusca = ""
                    idRegistroAtual = ""
                    nomeFilme = ""
                    nomeIndicado = ""
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Text("Buscar Outro Registro")
            }
        }
    }
}
