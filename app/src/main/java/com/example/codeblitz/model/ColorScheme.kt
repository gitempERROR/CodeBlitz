package com.example.codeblitz.model

import androidx.compose.ui.graphics.Color


data class ColorScheme(
    val name: String,
    val primary: Color,
    val tertiary: Color,
    val secondary: Color,
    val background: Color,
    val onBackground: Color,
    val secondaryContainer: Color,
)