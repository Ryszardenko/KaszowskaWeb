package com.machmudow.kaszowska.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color palette inspired by magdagrochulska.pl
object KaszowskaColors {
    val Cream = Color(0xFFF5F1ED)
    val DarkBrown = Color(0xFF2C2421)
    val SoftBeige = Color(0xFFE8DFD8)
    val Gold = Color(0xFFC9A87C)
    val LightGold = Color(0xFFE5D4B8)
    val TextDark = Color(0xFF1A1614)
    val TextLight = Color(0xFF666666)
    val White = Color(0xFFFFFFFF)
    val SoftGray = Color(0xFFF9F7F5)
}

private val LightColorScheme = lightColorScheme(
    primary = KaszowskaColors.Gold,
    onPrimary = KaszowskaColors.White,
    secondary = KaszowskaColors.DarkBrown,
    onSecondary = KaszowskaColors.White,
    background = KaszowskaColors.White,
    onBackground = KaszowskaColors.TextDark,
    surface = KaszowskaColors.Cream,
    onSurface = KaszowskaColors.TextDark,
)

@Composable
fun KaszowskaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}

