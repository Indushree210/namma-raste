package com.namma.raste.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.namma.raste.health.navigation.AppNavigation
import com.namma.raste.health.navigation.Screen
import com.namma.raste.health.ui.theme.NammaRasteHealthTheme

import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NammaRasteHealthTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(currentRoute)) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavigation(navController)
        }
    }
}

private fun shouldShowBottomBar(route: String?): Boolean {

    return route != null &&
            route != Screen.Splash.route &&
            route != Screen.Login.route
}

@Composable
fun BottomNavigationBar(
    navController: androidx.navigation.NavHostController,
    currentRoute: String?
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 8.dp
    ) {

        val items = listOf(

            Triple(
                Screen.Home.route,
                Icons.Default.GridView,
                "Home"
            ),

            Triple(
                Screen.Reports.route,
                Icons.Default.Description,
                "Reports"
            ),

            Triple(
                Screen.Scan.route,
                Icons.Default.AddAPhoto,
                "Scan"
            ),

            Triple(
                Screen.Map.route,
                Icons.Default.Map,
                "Map"
            ),

            Triple(
                Screen.Profile.route,
                Icons.Default.AccountCircle,
                "Profile"
            )
        )

        items.forEach { (route, icon, label) ->

            NavigationBarItem(

                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },

                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },

                selected = currentRoute == route,

                onClick = {

                    if (currentRoute != route) {

                        navController.navigate(route) {

                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }

                            launchSingleTop = true

                            restoreState = true
                        }
                    }
                },

                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = Color.White,

                    unselectedIconColor = Color.White.copy(alpha = 0.5f),

                    selectedTextColor = Color.White,

                    unselectedTextColor = Color.White.copy(alpha = 0.5f),

                    indicatorColor = Color.White.copy(alpha = 0.15f)
                )
            )
        }
    }
}