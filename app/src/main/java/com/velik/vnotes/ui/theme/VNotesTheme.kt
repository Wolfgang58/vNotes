package com.velik.vnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Açık kağıt tonları
val PaperBackground = Color(0xFFFFFBEA) // çok açık sarımsı
val PaperSurface = Color(0xFFFFF8DC)    // açık krem
val PaperPrimary = Color(0xFFB58B4C)    // kalem kahvesi
val PaperOnPrimary = Color(0xFFFFFFFF)
val PaperOnSurface = Color(0xFF3E2723)  // koyu kahve yazı

val LightColors = lightColorScheme(
    primary = PaperPrimary,
    onPrimary = PaperOnPrimary,
    background = PaperBackground,
    surface = PaperSurface,
    onSurface = PaperOnSurface
)

@Composable
fun VNotesTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
