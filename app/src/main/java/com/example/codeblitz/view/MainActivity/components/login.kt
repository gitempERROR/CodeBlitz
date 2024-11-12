package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.TextFieldCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentButtonCodeBlitz

@Preview
@Composable
fun login() {
    Box(
        modifier = Modifier.fillMaxHeight()
    )
    {
        Column(
            modifier = Modifier.background(color = CodeBlitzTheme.colors.background).fillMaxSize()
        ) {
            Row(
                modifier = Modifier.background(color = CodeBlitzTheme.colors.background).fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.width(35.dp).fillMaxHeight().background(color = CodeBlitzTheme.colors.primary)
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.width(35.dp).fillMaxHeight().background(color = CodeBlitzTheme.colors.primary)
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.width(35.dp).fillMaxHeight().background(color = CodeBlitzTheme.colors.primary)
                )
                Column(
                    modifier = Modifier.fillMaxSize().weight(25f)
                ) {
                    Box(
                        modifier = Modifier.height(50.dp).fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier.fillMaxSize().background(color = CodeBlitzTheme.colors.onBackground)
                    ) {
                        Spacer(
                            modifier = Modifier.weight(0.03f)
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.vector_logo_codebliz),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth().height(150.dp)
                        )
                        Spacer(
                            modifier = Modifier.weight(0.12f)
                        )
                        Column(
                            modifier = Modifier.fillMaxSize().weight(0.6f)
                        ) {
                            TextFieldCodeBlitz(
                                modifier = Modifier.padding(horizontal = 15.dp).fillMaxWidth(),
                                internalModifier = Modifier.fillMaxWidth(),
                                labelGap = 1.dp,
                                text = "Логин",
                                padding = 12.dp
                            )
                            Spacer(
                                modifier = Modifier.height(40.dp)
                            )
                            TextFieldCodeBlitz(
                                modifier = Modifier.padding(horizontal = 15.dp).fillMaxWidth(),
                                internalModifier = Modifier.fillMaxWidth(),
                                labelGap = 1.dp,
                                text = "Пароль",
                                padding = 12.dp
                            )
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                            TransparentButtonCodeBlitz(
                                text = "Зарегистрироваться",
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        }
        Column {
            Spacer(
                modifier = Modifier.height(49.dp)
            )
            Box(
                modifier = Modifier.background(color = CodeBlitzTheme.colors.tertiary).fillMaxWidth().height(10.dp)
            )
        }
    }
}