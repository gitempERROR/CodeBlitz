package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R

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
    ) {
        Text(
            text = text,
            style = CodeBlitzTheme.typography.titleSmall,
            color = CodeBlitzTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonCodeBlitz(
    text: String = "Button",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = CodeBlitzTheme.colors.background
        ),
        shape = RoundedCornerShape(15.dp)
    )
    {
        Text(
            text = text,
            style = CodeBlitzTheme.typography.titleMedium,
            color = CodeBlitzTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun IconButtonCodeBlitz(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    iconId: Int = R.drawable.pointer_back
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = CodeBlitzTheme.colors.background
        ),
        shape = RoundedCornerShape(15.dp)
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = "",
            tint = CodeBlitzTheme.colors.secondary
        )
    }
}
