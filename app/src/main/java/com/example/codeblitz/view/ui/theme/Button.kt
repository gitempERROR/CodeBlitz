package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R

//Прозрачная кнопка, только текст кнопки
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

//Основная кнопка приложения
@Composable
fun ButtonCodeBlitz(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String = "Button",
    onClick: () -> Unit = {},
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = CodeBlitzTheme.colors.background,
        disabledContainerColor = CodeBlitzTheme.colors.secondaryContainer
    ),
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .padding(top = 2.dp)
            .shadow(
                2.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(bottom = 2.dp),
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(15.dp),
        enabled = enabled
    )
    {
        Text(
            text = text,
            style = CodeBlitzTheme.typography.titleMedium,
            color = CodeBlitzTheme.colors.secondary,
            textAlign = TextAlign.Center,
            modifier = textModifier
        )
    }
}

//Кнопка с иконкой вместо текста
@Composable
fun IconButtonCodeBlitz(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    iconId: Int = R.drawable.pointer_back,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = CodeBlitzTheme.colors.background
    ),
    tint: Color = CodeBlitzTheme.colors.secondary
) {
    Button(
        modifier = modifier
            .padding(top = 2.dp)
            .shadow(
                2.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(bottom = 2.dp),
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(15.dp)
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = "",
            tint = tint,
            modifier = modifierIcon
                .fillMaxSize()
                .align(Alignment.CenterVertically)
                .padding(0.dp)
        )
    }
}

//Прозрачная кнопка с иконкой вместо текста
@Preview
@Composable
fun TransparentIconButtonCodeBlitz(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    iconId: Int = R.drawable.pointer_back,
    tint: Color = CodeBlitzTheme.colors.secondary
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(5.dp)
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = "",
            tint = tint,
            modifier = modifierIcon
        )
    }
}
