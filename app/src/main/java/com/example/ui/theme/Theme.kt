package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RecomecaColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = DarkGreenAccent,
    onPrimaryContainer = LightMintGreen,
    secondary = LightMintGreen,
    onSecondary = DarkBackground,
    secondaryContainer = DarkCard,
    onSecondaryContainer = TextPrimary,
    tertiary = AccentGold,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSecondaryBg,
    onSurface = TextPrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    error = AccentRed,
    onError = Color.White
)

@Composable
fun RecomecaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RecomecaColorScheme,
        typography = Typography,
        content = content
    )
}
