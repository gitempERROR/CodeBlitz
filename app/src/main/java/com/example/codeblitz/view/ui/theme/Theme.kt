package com.example.codeblitz.view.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

// Текущая тема приложения
private val colorScheme = MutableStateFlow(
    darkColorScheme(
        primary = Primary,
        secondary = Secondary,
        tertiary = Tertiary,
        background = Background,
        onBackground = OnBackground,
        secondaryContainer = SecondaryContainer,
    )
)

// Задание локальных цветов и стилей текста
val LocalColors = staticCompositionLocalOf { colorScheme.value }
val LocalTypography = staticCompositionLocalOf { Typography }

// Функция, отвечающая за тему приложения
@Composable
fun CodeBlitzTheme(
    typography: Typography = CodeBlitzTheme.typography,
    content: @Composable () -> Unit
) {
    // Обновление цветов приложения при их изменении
    val colors = colorScheme.collectAsState().value

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}

// Объект темы для получения цветов и стилей текста
object CodeBlitzTheme {
    val colors: ColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
    val typography: Typography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current

    // Функция для изменения цвета приложения
    fun changeTheme(newTheme: com.example.codeblitz.model.ColorScheme) {
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