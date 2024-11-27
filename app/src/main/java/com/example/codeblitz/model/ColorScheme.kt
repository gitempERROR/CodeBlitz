package com.example.codeblitz.model

import androidx.compose.ui.graphics.Color

//Класс с данными о цветовых схемах с цветами не в виде строк
data class ColorScheme(
    val name: String,
    val primary: Color,
    val tertiary: Color,
    val secondary: Color,
    val background: Color,
    val onBackground: Color,
    val secondaryContainer: Color,
)