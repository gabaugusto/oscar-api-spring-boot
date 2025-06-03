package com.sample.oscarinterface.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.oscarinterface.ui.components.BottomAppBarNavigation
import com.sample.oscarinterface.ui.screens.ApagarScreen
import com.sample.oscarinterface.ui.screens.AtualizarScreen
import com.sample.oscarinterface.ui.screens.HomeScreen
import com.sample.oscarinterface.ui.screens.InserirScreen
import com.sample.oscarinterface.ui.screens.ListaIndicacoesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Indicados ao Oscar")
                }
            )
        },
        bottomBar = { BottomAppBarNavigation(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen() }
            composable("listar") { ListaIndicacoesScreen() }
            composable("inserir") { InserirScreen() }
            composable("apagar") { ApagarScreen() }
            composable("atualizar") { AtualizarScreen() }
        }
    }
}