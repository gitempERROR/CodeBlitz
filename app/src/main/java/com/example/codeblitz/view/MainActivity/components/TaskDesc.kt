package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.ButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

@Preview
@Composable
fun TaskDesc() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
    ) {
        Box(
            modifier = Modifier
                .background(color = CodeBlitzTheme.colors.onBackground)
                .height(85.dp)
                .fillMaxWidth()
        ) {
            TransparentIconButtonCodeBlitz(
                modifier = Modifier
                    .padding(start = 25.dp, top = 3.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(bottom = 3.dp)
                    .size(60.dp)
                    .background(
                        color = CodeBlitzTheme.colors.background,
                        shape = RoundedCornerShape(50)
                    )
                    .align(Alignment.CenterStart),
                iconId = R.drawable.user,
                modifierIcon = Modifier.padding(5.dp),
                tint = CodeBlitzTheme.colors.primary
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 110.dp, end = 110.dp, top = 3.dp).height(60.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(bottom = 3.dp)
                    .background(
                        color = CodeBlitzTheme.colors.background,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = "Задание",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 20.dp),
                    style = CodeBlitzTheme.typography.titleMedium,
                    maxLines = 2,
                    color = CodeBlitzTheme.colors.tertiary
                )
            }
            IconButtonCodeBlitz(
                modifier = Modifier
                    .padding(end = 25.dp, top = 3.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(bottom = 3.dp)
                    .size(60.dp)
                    .align(Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CodeBlitzTheme.colors.background
                ),
                tint = CodeBlitzTheme.colors.primary
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, bottom = 20.dp, top = 2.dp)
                .shadow(
                    2.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(bottom = 2.dp)
                .fillMaxSize()
                .background(
                    color = CodeBlitzTheme.colors.primary, shape = RoundedCornerShape(10.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
                    .background(
                        color = CodeBlitzTheme.colors.background, shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp)
                ) {
                    Text(
                        text = "Empty",
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.tertiary,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    )
                    Box(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                            .background(
                                color = CodeBlitzTheme.colors.tertiary,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )
                    Text(
                        text = "После начала попытки стартует отсчёт времени.",
                        style = CodeBlitzTheme.typography.bodyMedium,
                        color = CodeBlitzTheme.colors.tertiary
                    )
                    Text(
                        text = "Возможна только одна попытка!",
                        style = CodeBlitzTheme.typography.bodyMedium,
                        color = CodeBlitzTheme.colors.secondary
                    )
                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )
                    ButtonCodeBlitz(
                        text = "Начать",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp)
                            .height(65.dp),
                        textModifier = Modifier.padding(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CodeBlitzTheme.colors.onBackground
                        )
                    )
                }
            }
        }
    }
}