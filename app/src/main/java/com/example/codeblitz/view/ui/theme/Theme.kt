package com.example.codeblitz.view.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.material3.Typography
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow

private val colorScheme = MutableStateFlow(darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    onBackground = OnBackground,
    secondaryContainer = SecondaryContainer,
))

val LocalColors = staticCompositionLocalOf { colorScheme.value }
val LocalTypography = staticCompositionLocalOf { Typography }

@Composable
fun CodeBlitzTheme(
    typography: Typography = CodeBlitzTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = colorScheme.collectAsState().value

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}

object CodeBlitzTheme {
    val colors : ColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
    val typography : Typography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current
    fun changeTheme (newTheme: com.example.codeblitz.model.ColorScheme) {
        colorScheme.value = colorScheme.value.copy(
            primary = newTheme.primary,
            tertiary = newTheme.tertiary,
            secondary = newTheme.secondary,
            background = newTheme.background,
            onBackground = newTheme.onBackground,
            secondaryContainer = newTheme.secondaryContainer
        )
    }
}