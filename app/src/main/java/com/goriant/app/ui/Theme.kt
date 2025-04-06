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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.goriant.app.R

// title
val H1 = 16.em
val TEXT_COLOR_PRIMARY = Color(0xFF00FFD5)

// headline
val TEXT_COLOR_SECONDARY = Color(0xFFD01F70)
val H2 = 36.sp
val H2_PADDING = 16.dp

// content
val H3 = 24.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFF26A69A),   // Fresh teal for a modern, casual vibe
    secondary = Color(0xFFFF8A65), // Soft, approachable orange accent
    background = Color(0xFFE0F7FA),// Light, airy cyan background for brightness
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF00897B),   // Deeper teal to maintain consistency in dark mode
    secondary = Color(0xFFD84315), // Rich, muted orange for a subtle pop
    background = Color(0xFF263238),// Dark neutral backdrop for comfort in low light
    surface = Color(0xFF37474F),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

val SuperShinyFont = FontFamily(
    Font(R.font.super_shiny)
)

val Typography = Typography(
    headlineSmall = TextStyle(
        fontFamily = SuperShinyFont,
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

