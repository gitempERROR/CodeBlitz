package com.example.codeblitz.view.ui.theme

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TransparentButtonCodeBlitz(
    text: String = "Button",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    )
    {
        Text(
            text = text,
            style = CodeBlitzTheme.typography.titleSmall,
            color = CodeBlitzTheme.colors.secondary
        )
    }
}
