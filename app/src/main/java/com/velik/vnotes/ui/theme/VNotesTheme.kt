package com.velik.vnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Purple40,
    onPrimary = PurpleGrey40,
    surface = PurpleGrey40,
    onSurface = PurpleGrey80
)

private val DarkColors = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple40,
    surface = PurpleGrey80,
    onSurface = PurpleGrey40
)

@Composable
fun VNotesTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
