package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.ProfileViewModel
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.view.ui.theme.ButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TextFieldCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

//Страница профиля
@Composable
fun Profile(
    controller: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    //Подписка на события навигации
    LaunchedEffect(
        viewModel.navigationStateFlow
    ) {
        viewModel.navigationStateFlow.collect { event ->
            event?.let {
                if (event.route != Routes.Login.route)
                    controller.navigate(viewModel.backRoute)
                else
                    controller.navigate(event.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.onBackground)
            .testTag("Profile")
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
                //Цвет иконки отличается от остальных страниц для обозначения текущей
                tint = CodeBlitzTheme.colors.secondary
            )
            Text(
                text = "Профиль",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 110.dp),
                style = CodeBlitzTheme.typography.titleMedium,
                maxLines = 2,
                color = CodeBlitzTheme.colors.tertiary
            )
            //Кнопка возврата на главную
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
                onClick = { viewModel.navigateBack() }
            )
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        //Поле для изменения имени
        TextFieldCodeBlitz(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxWidth()
                //Сброс значения полей при расфокусе, если не сохранены изменения
                .onFocusChanged { if (!it.isFocused) viewModel.refreshFields() },
            internalModifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            labelGap = 20.dp,
            label = "Имя",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true,
            text = viewModel.userData.firstname,
            onValueChange = { newValue ->
                viewModel.setUserData(
                    viewModel.userData.copy(firstname = newValue)
                )
            },
            //Сохранение данных в БД только при нажатии кнопки
            iconOnClick = { viewModel.updateUserData() }
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        //Поле для изменения фамилии
        TextFieldCodeBlitz(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxWidth()
                //Сброс значения полей при расфокусе, если не сохранены изменения
                .onFocusChanged { if (!it.isFocused) viewModel.refreshFields() },
            internalModifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            labelGap = 20.dp,
            label = "Фамилия",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true,
            text = viewModel.userData.surname,
            onValueChange = { newValue ->
                viewModel.setUserData(
                    viewModel.userData.copy(surname = newValue)
                )
            },
            //Сохранение данных в БД только при нажатии кнопки
            iconOnClick = { viewModel.updateUserData() }
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        TextFieldCodeBlitz(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxWidth()
                //Сброс значения полей при расфокусе, если не сохранены изменения
                .onFocusChanged { if (!it.isFocused) viewModel.refreshFields() },
            internalModifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            labelGap = 20.dp,
            label = "Имя пользователя",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true,
            text = viewModel.userData.nickname,
            onValueChange = { newValue ->
                viewModel.setUserData(
                    viewModel.userData.copy(nickname = newValue)
                )
            },
            //Сохранение данных в БД только при нажатии кнопки
            iconOnClick = { viewModel.updateUserData() }
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        //Кнопка выхода из аккаунта
        ButtonCodeBlitz(
            text = "Выйти",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 35.dp, end = 35.dp, bottom = 50.dp)
                .height(65.dp)
                .fillMaxWidth(),
            onClick = { viewModel.exit() }
        )
    }
}