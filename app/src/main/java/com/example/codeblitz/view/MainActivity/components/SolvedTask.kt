package com.example.codeblitz.view.MainActivity.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.codeblitz.R
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.CustomDropDownMenu
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.JetBrains
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SolvedTask() {
    val configuration = LocalConfiguration.current
    val vertical = remember {
        derivedStateOf { configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
    }

    val language = CodeLang.Python

    val parser = remember { PrettifyParser() }
    val themeState by remember { mutableStateOf(CodeThemeType.Monokai) }
    val theme = remember(themeState) { themeState.theme() }

    val options = mutableListOf("Python", "GO")
    val selectedText = remember { mutableStateOf(options[0]) }

    val code = """
        width = float(input("Введите ширину прямоугольника: "))
        height = float(input("Введите высоту прямоугольника: "))
        area = width * height
        print(f"Площадь прямоугольника: {area}")
    """.trimIndent()

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                annotatedString = parseCodeAsAnnotatedString(
                    parser = parser,
                    theme = theme,
                    lang = language,
                    code = code
                )
            )
        )
    }

    var taskOpened by remember { mutableStateOf(false) }
    val lineCount by remember { derivedStateOf { countAndFormatNewLines(textFieldValue) } }

    val containerHeight by remember { derivedStateOf { if (taskOpened) 800.dp else 85.dp } }
    val animatedContainerHeight by animateDpAsState(
        targetValue = containerHeight,
        label = "Container height",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val height by remember { derivedStateOf { if (taskOpened) 750.dp else 60.dp } }
    val animatedHeight by animateDpAsState(
        targetValue = height,
        label = "height",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val containerPadding by remember { derivedStateOf { if (taskOpened) 20.dp else 0.dp } }
    val animatedContainerPadding by animateDpAsState(
        targetValue = containerPadding,
        label = "padding",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val padding by remember { derivedStateOf { if (taskOpened) 10.dp else 0.dp } }
    val animatedPadding by animateDpAsState(
        targetValue = padding,
        label = "padding",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val rotation by remember { derivedStateOf { if (taskOpened) 180f else 0f } }
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        label = "rotation",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val onBackground = CodeBlitzTheme.colors.onBackground
    val background = CodeBlitzTheme.colors.background

    val color by remember { derivedStateOf { if (taskOpened) onBackground else background }}
    val animatedColor by animateColorAsState(
        targetValue = color,
        label = "color",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    Box(
        modifier = Modifier
            .height(animatedContainerHeight)
            .fillMaxWidth()
            .zIndex(4f)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 13.dp, bottom = animatedContainerPadding)
                .height(animatedHeight)
                .shadow(
                    2.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(bottom = 3.dp)
                .background(
                    color = CodeBlitzTheme.colors.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(animatedHeight)
                    .padding(bottom = animatedPadding)
                    .background(
                        color = CodeBlitzTheme.colors.background,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 20.dp, vertical = (15.5).dp),
                    text = "Задание",
                    textAlign = TextAlign.Center,
                    style = CodeBlitzTheme.typography.titleMedium,
                    maxLines = 2,
                    color = CodeBlitzTheme.colors.tertiary
                )
                TransparentIconButtonCodeBlitz(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    modifierIcon = Modifier.rotate(animatedRotation),
                    iconId = R.drawable.pointerdown,
                    tint = CodeBlitzTheme.colors.primary,
                    onClick = {taskOpened = !taskOpened}
                )
                if(taskOpened) {
                    Text(
                        text = "Empdaty EmpdatyEmpdatyEmpdaty EmpdatyEmpdaty Empdaty v Empdaty Empdaty EmpdatyEmpdaty  Empdaty EmpdatyEmpdatyEmpdatyv Empdatyv  vv EmpdatyEmpdaty EmpdatyEmp Empdaty EmpdatyEmpdatyEmpdaty EmpdatyEmpdaty Empdaty v Empdaty Empdaty EmpdatyEmpdaty  Empdaty EmpdatyEmpdatyEmpdatyv Empdatyv  vv EmpdatyEmpdaty EmpdatyEmpdaty Empdaty Empdaty EmpdatyEmpdatyEmpdaty EmpdatyEmpdaty Empdaty v Empdaty Empdaty EmpdatyEmpdaty  Empdaty EmpdatyEmpdatyEmpdatyv Empdatyv  vv EmpdatyEmpdaty EmpdatyEmpdaty Empdaty  Empdaty EmpdatyEmpdatyEmpdaty EmpdatyEmpdaty Empdaty v Empdaty Empdaty EmpdatyEmpdaty  Empdaty EmpdatyEmpdatyEmpdatyv Empdatyv  vv EmpdatyEmpdaty EmpdatyEmpdaty Empdaty Empdaty EmpdatyEmpdatyEmpdaty EmpdatyEmpdaty Empdaty v Empdaty Empdaty EmpdatyEmpdaty  Empdaty EmpdatyEmpdatyEmpdatyv Empdatyv  vv EmpdatyEmpdaty EmpdatyEmpdaty Empdatydaty Empdaty",
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.tertiary,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp, vertical = 85.dp)
                            .padding(bottom = 10.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    IconButtonCodeBlitz(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .size(65.dp)
                            .align(Alignment.BottomStart),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CodeBlitzTheme.colors.onBackground
                        ),
                        tint = CodeBlitzTheme.colors.primary,
                        iconId = R.drawable.pointerbackthin,
                        modifierIcon = Modifier.scale(1.6f)
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
            .zIndex(3f)
    ) {
        Box(
            modifier = Modifier
                .background(color = CodeBlitzTheme.colors.onBackground)
                .height(85.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = animatedColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 50.dp)
                    .width(1.dp)
                    .background(color = CodeBlitzTheme.colors.secondary)
            ){
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(
                        color = CodeBlitzTheme.colors.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    text = "Empty",
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 20.dp)
                        .align(Alignment.TopStart)
                        .background(
                            color = CodeBlitzTheme.colors.background,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(200.dp)
                        .padding(start = 10.dp),
                    style = CodeBlitzTheme.typography.displaySmall,
                    color = CodeBlitzTheme.colors.tertiary
                )
                Text(
                    text = "Empty",
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 20.dp)
                        .align(Alignment.TopEnd)
                        .background(
                            color = CodeBlitzTheme.colors.background,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(120.dp)
                        .padding(end = 10.dp),
                    style = CodeBlitzTheme.typography.displaySmall,
                    color = CodeBlitzTheme.colors.tertiary,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Empty",
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            color = CodeBlitzTheme.colors.background,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(150.dp)
                        .padding(start = 10.dp),
                    style = CodeBlitzTheme.typography.displaySmall,
                    color = CodeBlitzTheme.colors.tertiary
                )
                Text(
                    text = "Empty",
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp)
                        .align(Alignment.BottomEnd)
                        .background(
                            color = CodeBlitzTheme.colors.background,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(120.dp)
                        .padding(end = 10.dp),
                    style = CodeBlitzTheme.typography.displaySmall,
                    color = CodeBlitzTheme.colors.primary,
                    textAlign = TextAlign.Right
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = lineCount,
                    textAlign = TextAlign.Left,
                    style = CodeBlitzTheme.typography.bodyMedium,
                    color = CodeBlitzTheme.colors.secondaryContainer,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(60.dp)
                )
                val interactionSource = remember { MutableInteractionSource() }
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it.copy(
                            annotatedString = parseCodeAsAnnotatedString(
                                parser = parser,
                                theme = theme,
                                lang = language,
                                code = it.text
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = JetBrains,
                        fontWeight = FontWeight.Normal,
                        fontSize = (15 * ScreenDimensions.getScreenRatio()).sp,
                        lineHeight = (22 * ScreenDimensions.getScreenRatio()).sp,
                        letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp,
                        color = CodeBlitzTheme.colors.tertiary
                    ),
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    enabled = false
                ) { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = "",
                        innerTextField = innerTextField,
                        singleLine = false,
                        enabled = true,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        visualTransformation = VisualTransformation.None,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = CodeBlitzTheme.colors.tertiary,
                            unfocusedTextColor = CodeBlitzTheme.colors.tertiary,
                            focusedPlaceholderColor = CodeBlitzTheme.colors.tertiary,
                            unfocusedPlaceholderColor = CodeBlitzTheme.colors.tertiary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = CodeBlitzTheme.colors.tertiary,
                            errorCursorColor = CodeBlitzTheme.colors.tertiary
                        )
                    )
                }
            }
        }
    }
    if (vertical.value) {
        Column(
            modifier = Modifier.zIndex(3.5f)
        ) {
            Spacer(
                modifier = Modifier.height(85.dp)
            )
            Box(
                modifier = Modifier
                    .background(color = CodeBlitzTheme.colors.tertiary)
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}