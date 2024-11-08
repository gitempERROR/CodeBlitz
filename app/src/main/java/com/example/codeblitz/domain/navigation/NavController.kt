package com.example.codeblitz.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.Login.route
    ) {

    }
}
