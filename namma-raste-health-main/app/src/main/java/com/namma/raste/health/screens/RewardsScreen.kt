package com.namma.raste.health.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun RewardsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Civic Hero Rewards", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Current Points", style = MaterialTheme.typography.titleMedium)
                Text("1,250", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.primary)
                Text("Next Reward: Silver Badge at 1,500", style = MaterialTheme.typography.bodySmall)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Your Badges", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BadgeItem("Road Protector")
            BadgeItem("Civic Hero")
        }
    }
}

@Composable
fun BadgeItem(name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(modifier = Modifier.size(80.dp), shape = androidx.compose.foundation.shape.CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {}
        Text(name, style = MaterialTheme.typography.labelMedium)
    }
}
