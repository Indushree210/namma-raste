package com.namma.raste.health.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Scan : Screen("scan")
    object Map : Screen("map")
    object Reports : Screen("reports")
    object Profile : Screen("profile")
    object Rewards : Screen("rewards")
    object Admin : Screen("admin")
    object ReportConfirmation : Screen("confirmation/{imageBase64}") {
        fun createRoute(imageBase64: String) = "confirmation/$imageBase64"
    }
}
