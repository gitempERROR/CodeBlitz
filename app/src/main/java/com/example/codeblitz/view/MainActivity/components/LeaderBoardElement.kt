package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.TaskSolutions
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme

//Элемент таблицы лидеров
@Composable
fun LeaderBoardElement(
    place: Int = 0,
    time: Float = 0f,
    username: String = "Empty",
    isUser: Boolean = false,
    item: TaskSolutions,
    controller: NavController
) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .background(
                color = if (isUser) CodeBlitzTheme.colors.secondary else CodeBlitzTheme.colors.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                //Переход на страницу с решения задачи с передачей аргументов задачи
                controller.navigate(
                    "SolvedTask"
                            + "/${item.task_title}"
                            + "/${item.user_id.nickname}"
                            + "/${item.date}"
                            + "/${item.language_id.language_name}"
                            + "/${item.spent_time}"
                            + "/${item.code}"
                            + "/${item.task_desc}"
                            + "/${item.id}"
                )
            }
            .testTag("LeaderBoardElement")
    ) {
        Box(
            modifier = Modifier
                .height(65.dp)
                .fillMaxWidth()
                .background(
                    color = CodeBlitzTheme.colors.onBackground,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    //Отображение занятого места только для пользователей
                    if (!CurrentUser.isAdmin) {
                        Text(
                            text = "$place место",
                            style = CodeBlitzTheme.typography.titleSmall,
                            color = CodeBlitzTheme.colors.secondary
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 5.dp)
                                .background(color = CodeBlitzTheme.colors.background)
                        )
                    }
                    //Разделители для отцентровки времени при входе от администратора
                    if (CurrentUser.isAdmin) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Text(
                        text = "$time минут",
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.primary
                    )
                    //Разделители для отцентровки времени при входе от администратора
                    if (CurrentUser.isAdmin) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                //Особое обозначение решения для пользователя, его отправившего
                if (isUser) {
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .padding(horizontal = 10.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(40.dp)
                                .background(
                                    color = CodeBlitzTheme.colors.background,
                                    shape = RoundedCornerShape(50)
                                )
                        ) {
                            Text(
                                text = "Вы",
                                style = CodeBlitzTheme.typography.titleSmall,
                                color = CodeBlitzTheme.colors.secondary,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                } else {
                    //Отображение имени остальных пользователей
                    Text(
                        text = username,
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.tertiary,
                        modifier = Modifier
                            .weight(5f)
                            .padding(horizontal = 10.dp)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}