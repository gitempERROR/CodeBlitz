package com.example.codeblitz.view.MainActivity.components

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.EditorViewModel
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.CustomDropDownMenu
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.JetBrains
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

//Страница редактора
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Editor(controller: NavController, viewModel: EditorViewModel = hiltViewModel()) {

    LaunchedEffect(
        viewModel.navigationStateFlow
    ) {
        viewModel.navigationStateFlow.collect { event ->
            event?.let { controller.navigate(event.route) }
        }
    }

    //Переменная определяющая, открыто ли описание задания
    var taskOpened by remember { mutableStateOf(false) }
    //Переменная для визуализации числа строк в редакторе
    val lineCount by remember { derivedStateOf { countAndFormatNewLines(viewModel.textFieldValue) } }

    //Анимация высоты контейнера с описанием задачи
    val containerHeight by remember { derivedStateOf { if (taskOpened) 800.dp else 85.dp } }
    val animatedContainerHeight by animateDpAsState(
        targetValue = containerHeight,
        label = "Container height",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    //Анимация высоты описания задачи
    val height by remember { derivedStateOf { if (taskOpened) 750.dp else 60.dp } }
    val animatedHeight by animateDpAsState(
        targetValue = height,
        label = "height",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    //Анимация отступов контейнера описания задачи
    val containerPadding by remember { derivedStateOf { if (taskOpened) 20.dp else 0.dp } }
    val animatedContainerPadding by animateDpAsState(
        targetValue = containerPadding,
        label = "padding",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    //Анимация отступов описания задачи
    val padding by remember { derivedStateOf { if (taskOpened) 10.dp else 0.dp } }
    val animatedPadding by animateDpAsState(
        targetValue = padding,
        label = "padding",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    //Анимация поворота иконки
    val rotation by remember { derivedStateOf { if (taskOpened) 180f else 0f } }
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        label = "rotation",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    //Анимация цвета фона при открытии задачи
    val onBackground = CodeBlitzTheme.colors.onBackground
    val background = CodeBlitzTheme.colors.background
    val color by remember { derivedStateOf { if (taskOpened) onBackground else background } }
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
            .testTag("Editor")
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
                    text = viewModel.title,
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
                    onClick = { taskOpened = !taskOpened }
                )
                //Отображение описания задачи только при раскрытии
                if (taskOpened) {
                    Text(
                        text = viewModel.desc,
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.tertiary,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp, vertical = 85.dp)
                            .padding(bottom = 10.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    //Отображение кнопки выхода только при раскрытом описании
                    IconButtonCodeBlitz(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .size(65.dp)
                            .align(Alignment.BottomStart),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CodeBlitzTheme.colors.onBackground
                        ),
                        tint = CodeBlitzTheme.colors.secondary,
                        iconId = R.drawable.check,
                        modifierIcon = Modifier.scale(2.3f),
                        onClick = { viewModel.switchPopup() }
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
            CustomDropDownMenu(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                selectedText = viewModel.selectedOption,
                options = viewModel.options,
                onOptionSelected = { newValue -> viewModel.setSelectedLanguage(newValue) }
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 50.dp)
                    .width(1.dp)
                    .background(color = CodeBlitzTheme.colors.secondary)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                //Отображение номеров строк
                Text(
                    text = lineCount,
                    textAlign = TextAlign.Left,
                    style = CodeBlitzTheme.typography.bodyMedium,
                    color = CodeBlitzTheme.colors.secondaryContainer,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(60.dp)
                )
                //Основное поле для печати кода
                val interactionSource = remember { MutableInteractionSource() }
                BasicTextField(
                    value = viewModel.textFieldValue,
                    onValueChange = {
                        viewModel.changeCode(it)
                    },
                    textStyle = TextStyle(
                        fontFamily = JetBrains,
                        fontWeight = FontWeight.Normal,
                        fontSize = (15 * ScreenDimensions.getScreenRatio()).sp,
                        lineHeight = (22 * ScreenDimensions.getScreenRatio()).sp,
                        letterSpacing = (0.5 * ScreenDimensions.getScreenRatio()).sp,
                        color = CodeBlitzTheme.colors.tertiary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
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
    //Отображение всплывающего окна для завершения задач
    if (viewModel.popup) {
        Popup(
            onCloseClick = { viewModel.switchPopup() },
            onEndClick = { viewModel.endTask() }
        )
    }
}

//Функция для получения строки с номерами линий
fun countAndFormatNewLines(input: TextFieldValue): String {
    val newlineCount = input.text.count { it == '\n' || it == '\r' } + 1

    if (newlineCount <= 0) {
        return ""
    }

    return (1..newlineCount).joinToString("\n") { it.toString() }
}
