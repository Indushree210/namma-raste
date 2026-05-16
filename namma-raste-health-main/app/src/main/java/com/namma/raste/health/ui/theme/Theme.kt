package com.namma.raste.health.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2E7D32),
    secondary = Color(0xFF4CAF50),
    tertiary = Color(0xFF81C784)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    secondary = Color(0xFF4CAF50),
    tertiary = Color(0xFF81C784),
    background = Color(0xFFF9FAFB), // gray-50
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF111827), // gray-900
    onSurface = Color(0xFF111827), // gray-900
    surfaceVariant = Color(0xFFF3F4F6), // gray-100 for borders/alt surfaces
    onSurfaceVariant = Color(0xFF6B7280), // gray-500 for secondary text
)

@Composable
fun NammaRasteHealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
