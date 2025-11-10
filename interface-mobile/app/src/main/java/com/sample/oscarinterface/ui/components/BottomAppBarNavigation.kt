package com.sample.oscarinterface.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomAppBarNavigation(navController: NavController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = { navController.navigate("listar") }) {
                Icon(Icons.Filled.FilterList, contentDescription = "listar")
            }
            IconButton(onClick = { navController.navigate("inserir") }) {
                Icon(Icons.Filled.Add, contentDescription = "Inserir")
            }
            IconButton(onClick = { navController.navigate("atualizar") }) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = "Sobre o APP")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Ação extra */ }) {
                contentColorFor(backgroundColor = MaterialTheme.colorScheme.background)
                Icon(Icons.Filled.Star, contentDescription = "Destaque")
            }
        }
    )
}
