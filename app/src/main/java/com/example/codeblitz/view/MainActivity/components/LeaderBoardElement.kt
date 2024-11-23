package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme

@Composable
fun LeaderBoardElement(
    place: Int = 0,
    time: Float = 0f,
    username: String = "Empty",
    isUser: Boolean = false
) {
    Box(
       modifier = Modifier
           .height(75.dp)
           .fillMaxWidth()
           .background(
               color = if (isUser) CodeBlitzTheme.colors.secondary else CodeBlitzTheme.colors.primary,
               shape = RoundedCornerShape(10.dp)
           )
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
                    Text(
                        text = "$time минут",
                        style = CodeBlitzTheme.typography.titleSmall,
                        color = CodeBlitzTheme.colors.primary
                    )
                }
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
                }
                else {
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