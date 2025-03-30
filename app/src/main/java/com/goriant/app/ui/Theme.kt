package com.goriant.app.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.goriant.app.R

private val LightColors = lightColorScheme(
    primary = Color(0xFFFFA726),   // A bright orange
    secondary = Color(0xFFFFCC80), // Lighter orange accent
    background = Color(0xFFFFF3E0),// Light pastel background
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    // In a real app, pick suitable dark theme colors
    primary = Color(0xFFF57C00),
    secondary = Color(0xFFFFB74D),
    background = Color(0xFF212121),
    surface = Color(0xFF424242),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

val CasualFont = FontFamily(
    Font(R.font.casual_regular_font)
)

val Typography = Typography(
    headlineSmall = TextStyle(
        fontFamily = CasualFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    // define other styles as you like
)

@Composable
fun MemoryGameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Use the custom typography
        content = content
    )
}

