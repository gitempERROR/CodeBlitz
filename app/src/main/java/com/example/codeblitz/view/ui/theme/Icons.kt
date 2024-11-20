package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.codeblitz.domain.utils.ScreenDimensions


@Composable
fun ColorIcon(
    modifier: Modifier = Modifier,
    color: Color = CodeBlitzTheme.colors.primary
) {
    Box(
        modifier = modifier.size((27 * ScreenDimensions.getScreenRatio()).dp)
    ) {
        val colorGlow = CodeBlitzTheme.colors.primary
        Canvas(
            modifier = Modifier.fillMaxSize().zIndex(3f)
        ) {
            val center =
                Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colorGlow,
                        colorGlow,
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                center = center,
                radius = radius
            )
        }
        Canvas(
            modifier = Modifier.fillMaxSize().zIndex(3f)
        ) {
            val center =
                Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2 - 12
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color,
                        color
                    ),
                    center = center,
                    radius = radius
                ),
                center = center,
                radius = radius
            )
        }
    }
}
