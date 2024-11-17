package com.example.codeblitz.domain.navigation

sealed class Routes(val route: String) {
    data object Main        : Routes("Home")

    data object Login       : Routes("Login")

    data object Profile     : Routes("Profile")

    data object Register    : Routes("Register")

    data object TaskDesc    : Routes("TaskDesc")

    data object Settings    : Routes("Settings")

    data object SolvedTask  : Routes("SolvedTask")

    data object TaskSolving : Routes("TaskSolving")

    data object Leaderboard : Routes("Leaderboard")
}