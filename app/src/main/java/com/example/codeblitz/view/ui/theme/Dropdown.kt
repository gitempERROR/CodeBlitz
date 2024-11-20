package com.example.codeblitz.view.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.zIndex
import com.example.codeblitz.R
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.model.ColorScheme


//Всё, что ниже, стоило 15 часов жизни
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    options: MutableList<String> = mutableListOf("Python", "C#", "JavaScript", "C++", "Go"),
    onOptionSelected: (String) -> Unit = {},
    backgroundColor: Color = CodeBlitzTheme.colors.onBackground,
    selectedText: MutableState<String> = mutableStateOf("")
) {
    var expanded by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = CodeBlitzTheme.colors.copy(surface = backgroundColor),
        shapes = MaterialTheme.shapes.copy(
            RoundedCornerShape(10.dp),
            RoundedCornerShape(10.dp),
            RoundedCornerShape(10.dp),
            RoundedCornerShape(10.dp),
            RoundedCornerShape(10.dp)
        )
    ) {
        Column(
            modifier = modifier
                .width(185.dp)
                .background(color = Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = !expanded},
                modifier = Modifier.background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp)
                )
                    .width(185.dp)
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                BasicTextField(
                    modifier = Modifier.menuAnchor().height(35.dp).padding(start = 10.dp).fillMaxWidth(),
                    value = selectedText.value,
                    onValueChange = {},
                    readOnly = true,
                    maxLines = 1,
                    textStyle = CodeBlitzTheme.typography.displaySmall.copy(
                        color = CodeBlitzTheme.colors.tertiary
                    )
                ) { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = "Empty",
                            innerTextField = innerTextField,
                            singleLine = true,
                            enabled = true,
                            interactionSource = interactionSource,
                            contentPadding = PaddingValues(0.dp),
                            visualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = CodeBlitzTheme.colors.tertiary,
                                unfocusedTextColor = CodeBlitzTheme.colors.tertiary,
                                cursorColor = CodeBlitzTheme.colors.tertiary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ellipsis),
                                    contentDescription = "",
                                    tint = CodeBlitzTheme.colors.secondary
                                )
                            }
                        )
                    }
                Box(
                ) {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(185.dp)
                            .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        options.forEachIndexed { index, text ->
                            if (index != 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .padding(horizontal = 5.dp)
                                        .background(color = CodeBlitzTheme.colors.background)
                                )
                            }
                            DropdownMenuItem(
                                text = {
                                    Box(
                                        modifier = Modifier.width(170.dp).height(35.dp)
                                    ) {
                                        val color = CodeBlitzTheme.colors.secondary
                                        Text(
                                            text = text,
                                            style = CodeBlitzTheme.typography.displaySmall,
                                            color = CodeBlitzTheme.colors.tertiary,
                                            modifier = Modifier.height(35.dp)
                                        )
                                        if(options[index] == selectedText.value) {
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .padding(bottom = 10.dp)
                                                    .height(26.dp)
                                                    .width(26.dp)
                                            ){
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(8.dp)
                                                        .background(
                                                            color = backgroundColor,
                                                            shape = RoundedCornerShape(50)
                                                        )
                                                        .zIndex(4f)
                                                )
                                                Canvas(
                                                    modifier = Modifier.fillMaxSize().zIndex(3f)
                                                ) {
                                                    val center = Offset(size.width / 2, size.height / 2)
                                                    val radius = size.width / 2
                                                    drawCircle(
                                                        brush = Brush.radialGradient(
                                                            colors = listOf(
                                                                color,
                                                                Color.Transparent
                                                            ),
                                                            center = center,
                                                            radius = radius
                                                        ),
                                                        center = center,
                                                        radius = radius
                                                    )
                                                }
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.selected),
                                                    contentDescription = "",
                                                    tint = CodeBlitzTheme.colors.secondary,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .zIndex(4f)
                                                )
                                            }
                                        }
                                    }
                                       },
                                onClick = {
                                    selectedText.value = options[index]
                                    expanded = false
                                    options.removeAt(index)
                                    options.add(0, selectedText.value)
                                },
                                contentPadding = PaddingValues(start = 10.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                                modifier = Modifier
                                    .background(color = Color.Transparent)
                                    .height(35.dp)
                                    .width(180.dp)
                                    .padding(0.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CustomDropDownMenuColors(
    modifier: Modifier = Modifier,
    options: MutableList<ColorScheme> = mutableListOf(
        ColorScheme(
            "Темная",
            CodeBlitzTheme.colors.primary,
            CodeBlitzTheme.colors.tertiary,
            CodeBlitzTheme.colors.secondary,
            CodeBlitzTheme.colors.background,
            CodeBlitzTheme.colors.onBackground,
            CodeBlitzTheme.colors.secondaryContainer
        ),
        ColorScheme(
            "Темнее",
            CodeBlitzTheme.colors.primary,
            CodeBlitzTheme.colors.tertiary,
            CodeBlitzTheme.colors.secondary,
            CodeBlitzTheme.colors.background,
            CodeBlitzTheme.colors.onBackground,
            CodeBlitzTheme.colors.secondaryContainer)),
    onOptionSelected: (String) -> Unit = {},
    backgroundColor: Color = CodeBlitzTheme.colors.onBackground,
    selectedScheme: MutableState<ColorScheme> = mutableStateOf(
        ColorScheme("Темная",
            CodeBlitzTheme.colors.primary,
            CodeBlitzTheme.colors.tertiary,
            CodeBlitzTheme.colors.secondary,
            CodeBlitzTheme.colors.background,
            CodeBlitzTheme.colors.onBackground,
            CodeBlitzTheme.colors.secondaryContainer
        )
    )
) {
    var expanded by remember { mutableStateOf(false) }

    val options = remember { options }
    val selectedScheme = remember { selectedScheme }

    Column(
        modifier = modifier.background(backgroundColor, shape = RoundedCornerShape(10.dp))
    ) {
        if (!expanded) {
            SelectedElement(
                modifier = Modifier.clickable { expanded = !expanded }.padding(top = (5 / ScreenDimensions.getScreenRatio() / ScreenDimensions.getScreenRatio()).dp),
                selectedScheme = selectedScheme,
                backgroundColor = backgroundColor
            )
        }
        else {
            options.forEachIndexed { index, colorScheme ->
                if (index != 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 5.dp)
                            .background(color = CodeBlitzTheme.colors.onBackground)
                    )
                }
                DropDownElement(
                    modifier = Modifier
                        .clickable {
                            selectedScheme.value = options[index]
                            expanded = false
                            options.removeAt(index)
                            options.add(0, selectedScheme.value)
                        }
                        .padding(top = (5 / ScreenDimensions.getScreenRatio() / ScreenDimensions.getScreenRatio()).dp),
                    colorScheme = colorScheme,
                    selectedScheme = selectedScheme,
                    backgroundColor = backgroundColor
                )
            }
        }
    }
}


@Composable
fun SelectedElement(
    modifier: Modifier = Modifier,
    selectedScheme: MutableState<ColorScheme>,
    backgroundColor: Color = CodeBlitzTheme.colors.onBackground
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(35.dp).padding(start = (120 * ScreenDimensions.getScreenRatio()).dp, bottom = 6.dp)
        ) {
            ColorIcon(
                color = selectedScheme.value.background
            )
            Spacer(
                modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio()).dp)
            )
            ColorIcon(
                color = selectedScheme.value.primary
            )
            Spacer(
                modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
            )
            ColorIcon(
                color = selectedScheme.value.secondaryContainer
            )
            Spacer(
                modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
            )
            ColorIcon(
                color = selectedScheme.value.tertiary
            )
            Spacer(
                modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
            )
            ColorIcon(
                color = selectedScheme.value.secondary
            )
            Spacer(
                modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
            )
            ColorIcon(
                color = selectedScheme.value.onBackground
            )
        }
        Text(
            text = selectedScheme.value.name,
            style = CodeBlitzTheme.typography.displaySmall,
            color = CodeBlitzTheme.colors.tertiary,
            modifier = Modifier.height(35.dp).padding(start = 10.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(bottom = 10.dp, end = 10.dp)
                .height(26.dp)
                .width(26.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ellipsis),
                contentDescription = "",
                tint = CodeBlitzTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(4f)
            )
        }
    }
}


@Composable
fun DropDownElement(
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme,
    selectedScheme: MutableState<ColorScheme>,
    backgroundColor: Color = CodeBlitzTheme.colors.onBackground
) {
    Box(
        modifier = modifier.height(35.dp).fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            val color = CodeBlitzTheme.colors.secondary
            Row(
                modifier = Modifier.fillMaxSize().padding(start = (120 * ScreenDimensions.getScreenRatio()).dp, bottom = 10.dp)
            ) {
                ColorIcon(
                    color = colorScheme.background
                )
                Spacer(
                    modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio()).dp)
                )
                ColorIcon(
                    color = colorScheme.primary
                )
                Spacer(
                    modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
                )
                ColorIcon(
                    color = colorScheme.secondaryContainer
                )
                Spacer(
                    modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
                )
                ColorIcon(
                    color = colorScheme.tertiary
                )
                Spacer(
                    modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
                )
                ColorIcon(
                    color = colorScheme.secondary
                )
                Spacer(
                    modifier = Modifier.width((5 * ScreenDimensions.getScreenRatio() * ScreenDimensions.getScreenRatio()).dp)
                )
                ColorIcon(
                    color = colorScheme.onBackground
                )
            }
            Text(
                text = colorScheme.name,
                style = CodeBlitzTheme.typography.displaySmall,
                color = CodeBlitzTheme.colors.tertiary,
                modifier = Modifier.height(35.dp).padding(start = 10.dp)
            )
            if (colorScheme.name == selectedScheme.value.name) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(bottom = 10.dp, end = 10.dp)
                        .height(26.dp)
                        .width(26.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(50)
                            )
                            .zIndex(4f)
                    )
                    Canvas(
                        modifier = Modifier.fillMaxSize().zIndex(3f)
                    ) {
                        val center =
                            Offset(size.width / 2, size.height / 2)
                        val radius = size.width / 2
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    color,
                                    Color.Transparent
                                ),
                                center = center,
                                radius = radius
                            ),
                            center = center,
                            radius = radius
                        )
                    }
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.selected),
                        contentDescription = "",
                        tint = CodeBlitzTheme.colors.secondary,
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(4f)
                    )
                }
            }
        }
    }
}
