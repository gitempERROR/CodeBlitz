package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.CustomDropDownMenu
import com.example.codeblitz.view.ui.theme.CustomDropDownMenuColors
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

@Composable
fun Leaderboard() {
    val optionsTask = mutableListOf("Задание 1", "Задание 2")
    val selectedTask = remember { mutableStateOf(optionsTask[0]) }

    val optionsDate = mutableListOf("01.10.2024", "02.10.2024")
    val selectedDate = remember { mutableStateOf(optionsDate[0]) }

    val optionsLang = mutableListOf("Python", "GO")
    val selectedLang = remember { mutableStateOf(optionsLang[0]) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
    ) {
        Box(
            modifier = Modifier
                .background(color = CodeBlitzTheme.colors.background)
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
                        color = CodeBlitzTheme.colors.onBackground,
                        shape = RoundedCornerShape(50)
                    )
                    .align(Alignment.CenterStart),
                iconId = R.drawable.user,
                modifierIcon = Modifier.padding(5.dp),
                tint = CodeBlitzTheme.colors.primary
            )
            Text(
                text = "Таблица лидеров",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 110.dp),
                style = CodeBlitzTheme.typography.titleMedium,
                maxLines = 2,
                color = CodeBlitzTheme.colors.tertiary
            )
            IconButtonCodeBlitz(
                modifier = Modifier
                    .padding(end = 25.dp)
                    .size(60.dp)
                    .align(Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CodeBlitzTheme.colors.onBackground
                ),
                tint = CodeBlitzTheme.colors.primary,
                iconId = R.drawable.pointerbackthin
            )
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text(
                text = "Фильтры",
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                style = CodeBlitzTheme.typography.bodyLarge,
                color = CodeBlitzTheme.colors.tertiary
            )
            CustomDropDownMenu(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                selectedText = selectedTask,
                options = optionsTask,
                backgroundColor = CodeBlitzTheme.colors.background,
                dividerColor = CodeBlitzTheme.colors.onBackground
            )
            CustomDropDownMenu(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                selectedText = selectedDate,
                options = optionsDate,
                backgroundColor = CodeBlitzTheme.colors.background,
                dividerColor = CodeBlitzTheme.colors.onBackground
            )
            CustomDropDownMenu(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                selectedText = selectedLang,
                options = optionsLang,
                backgroundColor = CodeBlitzTheme.colors.background,
                dividerColor = CodeBlitzTheme.colors.onBackground
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = CodeBlitzTheme.colors.background,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier.padding(
                    start = 35.dp,
                    end = 35.dp,
                    top = 20.dp
                )
            ) {
                items(125){ item ->
                    LeaderBoardElement()
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )
                }
            }
        }
    }
}