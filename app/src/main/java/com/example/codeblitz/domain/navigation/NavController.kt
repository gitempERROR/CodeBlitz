package com.example.codeblitz.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.codeblitz.view.MainActivity.components.Editor
import com.example.codeblitz.view.MainActivity.components.Leaderboard
import com.example.codeblitz.view.MainActivity.components.Main
import com.example.codeblitz.view.MainActivity.components.Login
import com.example.codeblitz.view.MainActivity.components.Profile
import com.example.codeblitz.view.MainActivity.components.Register
import com.example.codeblitz.view.MainActivity.components.Settings
import com.example.codeblitz.view.MainActivity.components.SolvedTask
import com.example.codeblitz.view.MainActivity.components.TaskDesc

@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.SolvedTask.route
    ) {
        composable(Routes.Login.route) {
            Login()
        }
        composable(Routes.Register.route) {
            Register()
        }
        composable(Routes.Main.route) {
            Main()
        }
        composable(Routes.Profile.route) {
            Profile()
        }
        composable(Routes.TaskDesc.route) {
            TaskDesc()
        }
        composable(Routes.Editor.route) {
            Editor()
        }
        composable(Routes.Settings.route) {
            Settings()
        }
        composable(Routes.Leaderboard.route) {
            Leaderboard()
        }
        composable(Routes.SolvedTask.route) {
            SolvedTask()
        }
    }
}
