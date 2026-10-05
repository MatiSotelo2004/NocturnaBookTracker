package org.matias.nocturnatracker.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NocturnaDarkColorScheme = darkColorScheme(
    primary = NocturnaGold,
    onPrimary = Color.Black,
    primaryContainer = NocturnaGoldDark,
    onPrimaryContainer = NocturnaTextPrimary,
    secondary = NocturnaCrimson,
    onSecondary = Color.White,
    secondaryContainer = NocturnaCrimsonLight,
    onSecondaryContainer = Color.White,
    tertiary = NocturnaGoldLight,
    onTertiary = Color.Black,
    background = NocturnaPrimary,
    onBackground = NocturnaTextPrimary,
    surface = NocturnaSurface,
    onSurface = NocturnaTextPrimary,
    surfaceVariant = NocturnaSurfaceVariant,
    onSurfaceVariant = NocturnaTextSecondary,
    surfaceContainer = NocturnaSurfaceContainer,
    outline = NocturnaBorder,
    outlineVariant = NocturnaGoldBorder,
    error = NocturnaError,
    onError = Color.White
)

@Composable
fun NocturnaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NocturnaDarkColorScheme,
        typography = NocturnaTypography,
        content = content
    )
}
