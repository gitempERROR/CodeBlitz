package com.example.codeblitz.view.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography: Typography
    get() = Typography(
        titleMedium = TextStyle(
            fontFamily = Intro,
            fontWeight = FontWeight.Normal,
            fontSize = 25.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.5.sp
        ),
        titleSmall = TextStyle(
            fontFamily = JetBrains,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = JetBrains,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp
        ),
    )