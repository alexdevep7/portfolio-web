package org.artisancode.mywebkotlin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondary = SecondaryColor,
    onSecondary = OnSecondary,

    tertiary = TertiaryColor,
    onTertiary = OnTertiary,

    background = BackgroundDark,
    onBackground = OnSurface,

    surface = SurfaceDark,
    onSurface = OnSurface,

    error = Error,
    onError = OnError,

    outline = Outline,
    outlineVariant = OutlineVariant
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}