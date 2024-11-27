package com.example.codeblitz.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.codeblitz.view.MainActivity.components.AddTask
import com.example.codeblitz.view.MainActivity.components.Editor
import com.example.codeblitz.view.MainActivity.components.Leaderboard
import com.example.codeblitz.view.MainActivity.components.Login
import com.example.codeblitz.view.MainActivity.components.Main
import com.example.codeblitz.view.MainActivity.components.Profile
import com.example.codeblitz.view.MainActivity.components.Register
import com.example.codeblitz.view.MainActivity.components.Settings
import com.example.codeblitz.view.MainActivity.components.SolvedTask
import com.example.codeblitz.view.MainActivity.components.TaskDesc

//Навигационный контроллер
@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            Login(controller)
        }
        composable(Routes.Register.route) {
            Register(controller)
        }
        composable(Routes.Main.route) {
            Main(controller)
        }
        composable(Routes.Profile.route) {
            Profile(controller)
        }
        composable(Routes.TaskDesc.route) {
            TaskDesc(controller)
        }
        composable(Routes.Editor.route) {
            Editor(controller)
        }
        composable(Routes.Settings.route) {
            Settings(controller)
        }
        composable(Routes.Leaderboard.route) {
            Leaderboard(controller)
        }
        composable(Routes.SolvedTask.route) {
            SolvedTask(controller)
        }
        composable(Routes.AddTask.route) {
            AddTask(controller)
        }
    }
}
