package com.vaibhav.presentation.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = MediumBlue,
    primaryVariant = Black,
    secondary = Secondary,
    background = DarkBlue,
    surface = MediumBlue,
    onPrimary = White,
    onSecondary = Black,
    onBackground = White,
    onSurface = White,
)

@Composable
fun TicTacToeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}