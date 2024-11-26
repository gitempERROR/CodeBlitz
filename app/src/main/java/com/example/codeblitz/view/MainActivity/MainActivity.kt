package com.example.codeblitz.view.MainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codeblitz.domain.navigation.NavController
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.view.MainActivity.components.BottomBar
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val controller = rememberNavController()
            var showBottomBar by rememberSaveable { mutableStateOf(true) }
            val navBackStackEntry by controller.currentBackStackEntryAsState()

            val displayMetrics = resources.displayMetrics

            ScreenDimensions.init(displayMetrics)

            showBottomBar = when (navBackStackEntry?.destination?.route) {
                Routes.Login.route -> false
                Routes.Register.route -> false
                else -> true
            }

            CodeBlitzTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (showBottomBar) BottomBar(controller) }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavController(controller)
                    }
                }
            }
        }
    }
}