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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.LeaderboardViewModel
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.domain.utils.ScreenDimensions
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.CustomDropDownMenu
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

//Экран таблицы лидеров
@Composable
fun Leaderboard(controller: NavController, viewModel: LeaderboardViewModel = hiltViewModel()) {
    //Подписка на события навигации
    LaunchedEffect(
        viewModel.navigationStateFlow
    ) {
        viewModel.navigationStateFlow.collect { event ->
            event?.let { controller.navigate(event.route) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
            .testTag("LeaderBoard")
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
                tint = CodeBlitzTheme.colors.primary,
                onClick = { viewModel.navigateToProfile() }
            )
            Text(
                //Разный заголовок страницы в зависимости от роли
                text = if (CurrentUser.isAdmin) "Проверка решений" else "Таблица лидеров",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 110.dp),
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
                iconId = R.drawable.pointerbackthin,
                onClick = { viewModel.navigateToMain() }
            )
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = (35 * ScreenDimensions.getScreenRatio()).dp)
                .fillMaxWidth()
                .height(80.dp)
        ) {
            //Для администратора не доступен фильтр по дням, так как идёт проверка решений за текущий день, также убрана надпись "Фильтры"
            if (!CurrentUser.isAdmin)
                Text(
                    text = "Фильтры",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    style = CodeBlitzTheme.typography.bodyLarge,
                    color = CodeBlitzTheme.colors.tertiary
                )
            CustomDropDownMenu(
                modifier = Modifier
                    //Другое расположения для администратора
                    .align(if (CurrentUser.isAdmin) Alignment.CenterEnd else Alignment.TopEnd),
                selectedText = viewModel.selectedOptionTask,
                options = viewModel.optionsTask,
                backgroundColor = CodeBlitzTheme.colors.background,
                dividerColor = CodeBlitzTheme.colors.onBackground,
                onOptionSelected = { viewModel.updateFilters() }
            )
            //Для администратора не доступен фильтр по дням, так как идёт проверка решений за текущий день, также убрана надпись "Фильтры"
            if (!CurrentUser.isAdmin)
                CustomDropDownMenu(
                    modifier = Modifier
                        .align(Alignment.BottomStart),
                    selectedText = viewModel.selectedOptionDate,
                    options = viewModel.optionsDate,
                    backgroundColor = CodeBlitzTheme.colors.background,
                    dividerColor = CodeBlitzTheme.colors.onBackground,
                    onOptionSelected = { viewModel.updateFilters() }
                )
            CustomDropDownMenu(
                modifier = Modifier
                    //Другое расположения для администратора
                    .align(if (CurrentUser.isAdmin) Alignment.CenterStart else Alignment.BottomEnd),
                selectedText = viewModel.selectedOptionLang,
                options = viewModel.optionsLang,
                backgroundColor = CodeBlitzTheme.colors.background,
                dividerColor = CodeBlitzTheme.colors.onBackground,
                onOptionSelected = { viewModel.updateFilters() }
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
                items(viewModel.solutionsList.value) { item ->
                    LeaderBoardElement(
                        place = item.place,
                        time = item.spent_time,
                        username = item.user_id.nickname,
                        isUser = (item.user_id.nickname == CurrentUser.userData!!.nickname),
                        item = item,
                        controller = controller
                    )
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )
                }
            }
        }
    }
}