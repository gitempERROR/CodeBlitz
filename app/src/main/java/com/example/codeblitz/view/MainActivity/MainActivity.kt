package com.example.codeblitz.view.MainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codeblitz.domain.navigation.NavController
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.view.MainActivity.components.BottomBar
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import dagger.hilt.android.AndroidEntryPoint

//Основное приложение
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val controller = rememberNavController()
            var showBottomBar by rememberSaveable { mutableStateOf(true) }
            val navBackStackEntry by controller.currentBackStackEntryAsState()

            //Получение характеристик дисплея
            val displayMetrics = resources.displayMetrics

            //Инициализация объекта для получения соотношения экрана
            ScreenDimensions.init(displayMetrics)

            //Отображение нижней панели на всех экранах кроме логина и регистрации
            showBottomBar = when (navBackStackEntry?.destination?.route) {
                Routes.Login.route -> false
                Routes.Register.route -> false
                else -> true
            }

            CodeBlitzTheme(
                content = {
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
            )
        }
    }
}