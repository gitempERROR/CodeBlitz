package com.example.codeblitz.view.MainActivity.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.MainViewModel
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

//Главная страница
@Composable
fun Main(controller: NavController, viewModel: MainViewModel = hiltViewModel()) {
    //Подписка на события навигации
    LaunchedEffect(
        viewModel.navigationStateFlow
    ) {
        viewModel.navigationStateFlow.collect { event ->
            event?.let {
                if (event.route != Routes.Profile.route)
                    controller.navigate(event.route)
                else {
                    try {
                        controller.navigate(
                            "Profile" + "/${Routes.Main.route}"
                        )
                    } catch (e: Exception) {
                        Log.e("navigation to profile", e.toString())
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
            .testTag("Main")
    ) {
        Box(
            modifier = Modifier
                .background(color = CodeBlitzTheme.colors.background)
                .height(85.dp)
                .fillMaxWidth()
        ) {
            //Кнопка перехода в профиль
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
                tint = CodeBlitzTheme.colors.primary,
                onClick = { viewModel.navigateToProfile() }
            )
            Text(
                text = if (CurrentUser.isAdmin) "Задания на завтра" else "Задания на сегодня",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 110.dp),
                style = CodeBlitzTheme.typography.titleMedium,
                maxLines = 2,
                color = CodeBlitzTheme.colors.tertiary
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.lightning),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 25.dp, top = 10.dp),
                tint = CodeBlitzTheme.colors.secondary,
                contentDescription = ""
            )
        }
        //Другая генерация блоков при входе от администратора
        if (CurrentUser.isAdmin) {
            var count: Int by remember { mutableIntStateOf(0) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                count = 0
                viewModel.tasks.forEach { task ->
                    TaskElement(
                        controller = controller,
                        title = "Задание " + task.day_task_id.toString(),
                        desc = task.task_description,
                        id = task.id,
                        status = task.task_status
                    )
                    //Подсчет количества сгенерированных заполненных блоков
                    count += 1
                }
                //Генерация пустых блоков с кнопкой добавления
                while (count < 2) {
                    EmptyTaskElement(
                        controller = controller,
                        itemID = count.toString()
                    )
                    count += 1
                }
            }
        } else {
            //Стандартная генерация блоков с задачами при входе от пользователя
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
            ) {
                items(viewModel.tasks) { task ->
                    TaskElement(
                        controller = controller,
                        title = "Задание " + task.day_task_id.toString(),
                        desc = task.task_description,
                        id = task.id,
                        status = task.task_status
                    )
                }
            }
        }
    }
}
