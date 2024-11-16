package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblitz.R
import com.example.codeblitz.domain.utils.ScreenDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextFieldCodeBlitz(
    modifier: Modifier = Modifier,
    labelGap: Dp = 5.dp,
    internalModifier: Modifier = Modifier,
    text: String = "TextField_label",
    padding: Dp = 10.dp,
    onValueChange: (String) -> Unit = {},
    isPassword: Boolean = false
) {
    Column(
        modifier = modifier
    ) {
        var shown by remember { mutableStateOf(if (isPassword) isPassword else null) }
        Text(
            text = text,
            style = CodeBlitzTheme.typography.titleSmall,
            color = CodeBlitzTheme.colors.tertiary
        )
        Spacer(
            modifier = Modifier.height(labelGap)
        )
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = "Empty",
            onValueChange = onValueChange,
            modifier = internalModifier,
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = JetBrains,
                fontWeight = FontWeight.Normal,
                fontSize = (14 * ScreenDimensions.getScreenRatio()).sp,
                lineHeight = (20 * ScreenDimensions.getScreenRatio()).sp,
                letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp,
                color = CodeBlitzTheme.colors.primary
            ),
            visualTransformation = if (shown != null) (if (!shown!!) PasswordVisualTransformation() else VisualTransformation.None) else VisualTransformation.None
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = "Empty",
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(padding), // this is how you can remove the padding
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CodeBlitzTheme.colors.secondaryContainer,
                    unfocusedContainerColor = CodeBlitzTheme.colors.secondaryContainer,
                    focusedTextColor = CodeBlitzTheme.colors.primary,
                    unfocusedTextColor = CodeBlitzTheme.colors.primary,
                    focusedPlaceholderColor = CodeBlitzTheme.colors.primary,
                    unfocusedPlaceholderColor = CodeBlitzTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    if (isPassword) {
                        Icon(
                            imageVector = if (shown!!) ImageVector.vectorResource(R.drawable.open_eye)
                            else ImageVector.vectorResource((R.drawable.closed_eye)),
                            modifier = Modifier.clickable { shown = !shown!! },
                            contentDescription = "",
                            tint = CodeBlitzTheme.colors.primary
                        )
                    } else { }
                },
            )
        }
    }
}

