package com.namma.raste.health.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.namma.raste.health.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {

        delay(3000)

        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Government of India",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "NAMMA RASTE",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "HEALTH",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                letterSpacing = 8.sp,
                modifier = Modifier.offset(y = (-8).dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Smart Road Damage Detection System",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            color = Color.White.copy(alpha = 0.5f),
            strokeWidth = 2.dp
        )
    }
}