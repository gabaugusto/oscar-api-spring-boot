package com.sample.oscarinterface.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.oscarinterface.data.models.IndicacaoOscar // Importando a classe existente

@Composable
fun IndicacaoItem(indicacao: IndicacaoOscar) { // Alterado de Int para IndicacaoOscar
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = indicacao.nomeDoIndicado,
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = "Filme: ${indicacao.nomeDoFilme}")
            Text(text = "Categoria: ${indicacao.categoria}")
            Text(text = "Ano: ${indicacao.anoCerimonia}")
            Text(text = if (indicacao.vencedor) "🏆 Vencedor" else "Indicado")
        }
    }
}