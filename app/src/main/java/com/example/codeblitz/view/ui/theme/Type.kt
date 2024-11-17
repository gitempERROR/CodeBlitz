package com.example.codeblitz.view.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.codeblitz.domain.utils.ScreenDimensions

// Set of Material typography styles to start with
val Typography: Typography
    get() = Typography(
        titleMedium = TextStyle(
            fontFamily = Intro,
            fontWeight = FontWeight.Normal,
            fontSize = (25 * ScreenDimensions.getScreenRatio()).sp,
            lineHeight = (28* ScreenDimensions.getScreenRatio()).sp,
            letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp
        ),
        titleSmall = TextStyle(
            fontFamily = JetBrains,
            fontWeight = FontWeight.Normal,
            fontSize = (21 * ScreenDimensions.getScreenRatio()).sp,
            lineHeight = (26 * ScreenDimensions.getScreenRatio()).sp,
            letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = JetBrains,
            fontWeight = FontWeight.Normal,
            fontSize = (15 * ScreenDimensions.getScreenRatio()).sp,
            lineHeight = (20 * ScreenDimensions.getScreenRatio()).sp,
            letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = JetBrains,
            fontWeight = FontWeight.Bold,
            fontSize = (25 * ScreenDimensions.getScreenRatio()).sp,
            lineHeight = (28 * ScreenDimensions.getScreenRatio()).sp,
            letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp
        )
    )