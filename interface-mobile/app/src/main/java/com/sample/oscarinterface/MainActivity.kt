package com.sample.oscarinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.sample.oscarinterface.ui.NavGraph
import com.sample.oscarinterface.ui.theme.OscarInterfaceTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OscarInterfaceTheme {
                NavGraph()
            }
        }
    }
}
