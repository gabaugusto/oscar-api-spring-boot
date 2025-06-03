package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

@Composable
fun FiltrosScreen(
    viewModel: OscarViewModel,
    onClose: () -> Unit // Fecha a tela de filtros
) {
    var ano by remember { mutableStateOf<Int?>(null) }
    var categoria by remember { mutableStateOf("") }
    var vencedor by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Campo para Ano
        OutlinedTextField(
            value = ano?.toString() ?: "",
            onValueChange = { ano = it.toIntOrNull() },
            label = { Text("Ano da Cerimônia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para Categoria
        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoria (ex: ACTRESS)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Checkbox para Vencedores
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = vencedor == true,
                onCheckedChange = { vencedor = if (it) true else null }
            )
            Text("Somente vencedores", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botões de Ação
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.limparFiltros()
                    onClose()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("Limpar")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.filtrarIndicacoes(ano, categoria.ifEmpty { null }, vencedor)
                    onClose()
                }
            ) {
                Text("Aplicar Filtros")
            }
        }
    }
}