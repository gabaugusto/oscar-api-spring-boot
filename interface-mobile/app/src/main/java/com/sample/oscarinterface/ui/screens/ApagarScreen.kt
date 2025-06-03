package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.oscarinterface.ui.viewmodels.OscarViewModel

@Composable
fun ApagarScreen(viewModel: OscarViewModel = viewModel()) {
    val indicacoes by viewModel.indicacoes.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(indicacoes) { indicacao ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(indicacao.nomeDoFilme, modifier = Modifier.weight(1f))
            }
            IconButton(onClick = { viewModel.apagarIndicacao(indicacao.id.toString()) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Apagar", tint = Color.Red)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
