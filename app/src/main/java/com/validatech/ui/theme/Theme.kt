package com.validatech.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = AccentGreen,
    tertiary = AccentOrange,
    background = SurfaceLight,
    surface = SurfaceLight
)

private val darkScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentGreen,
    tertiary = AccentOrange
)

@Composable
fun ValidaTechTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightScheme,
        typography = Typography,
        content = content
    )
}
