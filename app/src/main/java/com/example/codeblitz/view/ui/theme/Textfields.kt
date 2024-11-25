package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
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
    label: String = "TextField_label",
    text: String = "Empty",
    paddingValues: PaddingValues = PaddingValues(10.dp),
    onValueChange: (String) -> Unit = {},
    isPassword: Boolean = false,
    isSettings: Boolean = false,
    style: TextStyle = CodeBlitzTheme.typography.titleSmall,
    iconOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        var shown by remember { mutableStateOf(if (isPassword) false else null) }
        var isFocused by remember { mutableStateOf(false)}
        Text(
            text = label,
            style = style,
            color = CodeBlitzTheme.colors.tertiary
        )
        Spacer(
            modifier = Modifier.height(labelGap)
        )
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = internalModifier
                .onFocusChanged { isFocused = it.isFocused }
                .padding(top = 3.dp)
                .shadow(
                    2.dp,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(bottom = 3.dp),
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,
            textStyle = if (!isSettings) TextStyle(
                fontFamily = JetBrains,
                fontWeight = FontWeight.Normal,
                fontSize = (15 * ScreenDimensions.getScreenRatio()).sp,
                lineHeight = (20 * ScreenDimensions.getScreenRatio()).sp,
                letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp,
                color = CodeBlitzTheme.colors.primary
            ) else TextStyle(
                fontFamily = JetBrains,
                fontWeight = FontWeight.Normal,
                fontSize = (21 * ScreenDimensions.getScreenRatio()).sp,
                lineHeight = (26 * ScreenDimensions.getScreenRatio()).sp,
                letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp,
                color = if (!isFocused) CodeBlitzTheme.colors.tertiary else CodeBlitzTheme.colors.primary
            ),
            visualTransformation = if (shown != null) (if (!shown!!) PasswordVisualTransformation() else VisualTransformation.None) else VisualTransformation.None
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = paddingValues, // this is how you can remove the padding
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CodeBlitzTheme.colors.secondaryContainer,
                    unfocusedContainerColor = if (isSettings) CodeBlitzTheme.colors.background else CodeBlitzTheme.colors.secondaryContainer,
                    focusedTextColor = CodeBlitzTheme.colors.primary,
                    unfocusedTextColor = if (isSettings) CodeBlitzTheme.colors.secondary else CodeBlitzTheme.colors.primary,
                    focusedPlaceholderColor = CodeBlitzTheme.colors.primary,
                    unfocusedPlaceholderColor = if (isSettings) CodeBlitzTheme.colors.secondary else CodeBlitzTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = CodeBlitzTheme.colors.tertiary
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
                    } else {
                        if(isSettings) {
                            Icon(
                                imageVector = if (isFocused) ImageVector.vectorResource(R.drawable.check)
                                else ImageVector.vectorResource((R.drawable.pen)),
                                modifier = if (isFocused) Modifier.clickable {iconOnClick()} else Modifier,
                                contentDescription = "",
                                tint = if (isFocused) CodeBlitzTheme.colors.primary else CodeBlitzTheme.colors.secondary
                            )
                        }
                    }
                },
            )
        }
    }
}

