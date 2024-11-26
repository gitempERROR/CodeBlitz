package com.example.codeblitz.domain.navigation

sealed class Routes(val route: String) {
    data object Main        : Routes("Main")

    data object Login       : Routes("Login")

    data object Editor      : Routes("Editor/{title}/{desc}/{status}/{id}")

    data object Profile     : Routes("Profile/{backRoute}")

    data object Register    : Routes("Register")

    data object TaskDesc    : Routes("TaskDesc/{title}/{desc}/{status}/{id}")

    data object Settings    : Routes("Settings")

    data object SolvedTask  : Routes("SolvedTask")

    data object Leaderboard : Routes("Leaderboard")
}