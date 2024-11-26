package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.ButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

@Composable
fun Popup(
    onCloseClick: () -> Unit = {},
    onEndClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(10f)
    ) {
        Box(
            modifier = Modifier
                .height(165.dp)
                .width(310.dp)
                .background(
                    color = CodeBlitzTheme.colors.onBackground,
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, top = 10.dp)
            ) {
                Row(

                ) {
                    Text(
                        text = "Вы уверены, что хотите завершить попытку?",
                        style = CodeBlitzTheme.typography.bodyMedium,
                        color = CodeBlitzTheme.colors.tertiary,
                        modifier = Modifier.weight(0.8f)
                    )
                    TransparentIconButtonCodeBlitz(
                        onClick = { onCloseClick() },
                        modifier = Modifier
                            .weight(0.2f)
                            .align(Alignment.Top),
                        modifierIcon = Modifier.align(Alignment.Top),
                        iconId = R.drawable.cross,
                        tint = CodeBlitzTheme.colors.secondary
                    )
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Text(
                    text = "Возможна только одна попытка!",
                    style = CodeBlitzTheme.typography.bodyMedium,
                    color = CodeBlitzTheme.colors.secondary
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                ButtonCodeBlitz(
                    text = "Закончить",
                    onClick = { onEndClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                )
            }
        }
    }
}