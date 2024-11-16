package com.example.codeblitz.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.codeblitz.view.MainActivity.components.login
import com.example.codeblitz.view.MainActivity.components.register

@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.Register.route
    ) {
        composable(Routes.Login.route) {
            login()
        }
        composable(Routes.Register.route){
            register()
        }
    }
}
