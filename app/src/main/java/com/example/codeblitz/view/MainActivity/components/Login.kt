package com.example.codeblitz.view.MainActivity.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.codeblitz.R
import com.example.codeblitz.domain.LoginViewModel
import com.example.codeblitz.view.ui.theme.ButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.TextFieldCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentButtonCodeBlitz

//Страница входа
@Composable
fun Login(controller: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    //Получение ориентации экрана
    val configuration = LocalConfiguration.current
    val vertical = remember {
        derivedStateOf { configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
    }
    //Подписка на события навигации
    LaunchedEffect(
        viewModel.navigationStateFlow
    ) {
        viewModel.navigationStateFlow.collect { event ->
            event?.let { controller.navigate(event.route) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CodeBlitzTheme.colors.background)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(3f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        //Разная ширина декоративных полос при повороте экрана
                        .weight(3.3f / (if (vertical.value) 1 else (3 / 2)))
                        .fillMaxHeight()
                        .background(color = CodeBlitzTheme.colors.primary)
                )
                Spacer(
                    modifier = Modifier.weight(1.2f / (if (vertical.value) 1 else (3 / 2)))
                )
                Box(
                    modifier = Modifier
                        //Разная ширина декоративных полос при повороте экрана
                        .weight(3.3f / (if (vertical.value) 1 else (3 / 2)))
                        .fillMaxHeight()
                        .background(color = CodeBlitzTheme.colors.primary)
                )
                Spacer(
                    modifier = Modifier.weight(1.2f / (if (vertical.value) 1 else (3 / 2)))
                )
                Box(
                    modifier = Modifier
                        //Разная ширина декоративных полос при повороте экрана
                        .weight(3.3f / (if (vertical.value) 1 else (3 / 2)))
                        .fillMaxHeight()
                        .background(color = CodeBlitzTheme.colors.primary)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(27f)
                ) {
                    //Отступ вверху только при вертикальном экране
                    if (vertical.value) {
                        Spacer(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = CodeBlitzTheme.colors.onBackground)
                    ) {
                        //Логотип приложения только при вертикальном экране
                        if (vertical.value) {
                            Spacer(
                                modifier = Modifier.weight(0.03f)
                            )
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.vector_logo_codebliz),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.15f)
                            )
                            Spacer(
                                modifier = Modifier.weight(0.1f)
                            )
                        }
                        //Отступ вверху страницы при горизонтальном экране
                        else {
                            Spacer(
                                modifier = Modifier.height(30.dp)
                            )
                        }
                        Column(
                            //Добавление прокрутки для страницы при горизонтальном экране
                            modifier = if (vertical.value) Modifier
                                .fillMaxSize()
                                .weight(0.6f)
                            else Modifier
                                .fillMaxSize()
                                .weight(0.6f)
                                .verticalScroll(
                                    rememberScrollState()
                                )
                        ) {
                            //Поле логина
                            TextFieldCodeBlitz(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .fillMaxWidth(),
                                internalModifier = Modifier.fillMaxWidth(),
                                labelGap = 1.dp,
                                label = "Логин",
                                text = viewModel.loginData.login,
                                onValueChange = {newValue ->
                                    viewModel.updateLoginData(
                                        viewModel.loginData.copy(login = newValue)
                                    )
                                },
                                paddingValues = PaddingValues(12.dp)
                            )
                            Spacer(
                                modifier = Modifier.height(40.dp)
                            )
                            //Поле пароля
                            TextFieldCodeBlitz(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .fillMaxWidth(),
                                internalModifier = Modifier.fillMaxWidth(),
                                labelGap = 1.dp,
                                label = "Пароль",
                                text = viewModel.loginData.password,
                                onValueChange = {newValue ->
                                    viewModel.updateLoginData(
                                        viewModel.loginData.copy(password = newValue)
                                    )
                                },
                                paddingValues = PaddingValues(12.dp),
                                isPassword = true
                            )
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                            //Кнопка перехода на окно регистрации
                            TransparentButtonCodeBlitz(
                                text = "Зарегистрироваться",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                onClick = { viewModel.navigateToRegister() }
                            )
                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )
                            //Отображение сообщения о некорректных данных при ошибке входа
                            if (viewModel.isError) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                        .background(
                                            color = CodeBlitzTheme.colors.background,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                ){
                                    Text(
                                        "Некорректные данные для входа!",
                                        style = CodeBlitzTheme.typography.bodyMedium,
                                        color = CodeBlitzTheme.colors.tertiary,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(horizontal = 12.dp, vertical = 12.dp),
                                        textAlign = TextAlign.Center,
                                        maxLines = 2
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.height(110.dp)
                            )
                        }
                    }
                }
            }
        }
        //Отображение горизонтальной полосы вверху экрана только при вертикальном экране
        if (vertical.value) {
            Column(
                modifier = Modifier.zIndex(4f)
            ) {
                Spacer(
                    modifier = Modifier.height(49.dp)
                )
                Box(
                    modifier = Modifier
                        .background(color = CodeBlitzTheme.colors.tertiary)
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }
        //Блоки для корректного отображения кнопки входа
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(
                    color = CodeBlitzTheme.colors.primary,
                    shape = RoundedCornerShape(88.dp)
                )
                .height(155.dp)
                .align(Alignment.BottomCenter)
                .zIndex(2f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(
                    color = CodeBlitzTheme.colors.primary,
                )
                .height(88.dp)
                .align(Alignment.BottomCenter)
                .zIndex(2f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 65.dp)
                .background(
                    color = CodeBlitzTheme.colors.onBackground,
                )
                .height(40.dp)
                .align(Alignment.BottomCenter)
                .zIndex(4f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 105.dp)
                .background(
                    color = CodeBlitzTheme.colors.onBackground,
                )
                .height(110.dp)
                .align(Alignment.BottomEnd)
                .zIndex(4f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 65.dp)
                .background(
                    color = CodeBlitzTheme.colors.onBackground,
                    shape = RoundedCornerShape(40.dp)
                )
                .height(110.dp)
                .align(Alignment.BottomCenter)
                .zIndex(4f)
        ){
            //Кнопка входа
            ButtonCodeBlitz(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(65.dp)
                    .align(Alignment.Center)
                    .zIndex(4f)
                    .offset(y = 10.dp),
                text = "Вход",
                onClick = { viewModel.login() },
                //Кнопка доступна только если заполнены поля
                enabled = viewModel.isButtonEnabled
            )
        }
    }
}