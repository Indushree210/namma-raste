package com.namma.raste.health.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.namma.raste.health.screens.*
import com.namma.raste.health.screens.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Scan.route) { ScanScreen(navController) }
        composable(Screen.Map.route) { MapScreen() }
        composable(Screen.Reports.route) { MyReportsScreen(navController) }
        composable(Screen.Admin.route) { AdminPanelScreen(navController) }
        composable(Screen.Rewards.route) { RewardsScreen(navController) }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
