package com.sample.oscarinterface.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Tela inicial do aplicativo com opções de navegação.
 * 
 * Esta tela apresenta cards clicáveis com ícones para acessar as diferentes funcionalidades:
 * - Inserir: Adicionar nova indicação
 * - Buscar: Procurar indicação por ID do Registro
 * - Atualizar: Editar indicação existente
 * - Apagar: Remover indicação
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de boas-vindas
        Text(
            text = "Bem-vindo ao Oscar Interface!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Escolha uma opção abaixo:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Grid de opções (2 colunas)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Inserir
            NavigationCard(
                title = "Inserir",
                description = "Adicionar nova indicação",
                icon = Icons.Filled.Add,
                onClick = { navController.navigate("inserir") },
                modifier = Modifier.weight(1f)
            )
            
            // Card Buscar
            NavigationCard(
                title = "Buscar",
                description = "Procurar por ID",
                icon = Icons.Filled.Search,
                onClick = { navController.navigate("buscar") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Atualizar
            NavigationCard(
                title = "Atualizar",
                description = "Editar indicação",
                icon = Icons.Filled.Edit,
                onClick = { navController.navigate("atualizar") },
                modifier = Modifier.weight(1f)
            )
            
            // Card Apagar
            NavigationCard(
                title = "Apagar",
                description = "Remover indicação",
                icon = Icons.Filled.Delete,
                onClick = { navController.navigate("apagar") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Componente reutilizável para criar cards de navegação.
 * 
 * Este componente cria um card clicável com ícone e texto.
 * Usado na HomeScreen para navegar entre as telas.
 */
@Composable
fun NavigationCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .height(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}