package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

//Нижняя панель с кнопками навигации
@Composable
fun BottomBar(
    controller: NavController
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TransparentIconButtonCodeBlitz(
            modifier = Modifier
                .offset(y = -(35.dp))
                .size(75.dp)
                .scale(1.3f)
                .shadow(
                    2.dp,
                    shape = RoundedCornerShape(50)
                )
                .border(
                    7.5.dp,
                    color = CodeBlitzTheme.colors.onBackground,
                    shape = RoundedCornerShape(50)
                )
                .background(
                    color = CodeBlitzTheme.colors.background,
                    shape = RoundedCornerShape(50)
                )
                .align(Alignment.BottomCenter)
                .zIndex(4f),
            iconId = R.drawable.home,
            modifierIcon = Modifier.padding(13.dp),
            onClick = { controller.navigate(Routes.Main.route) }
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .background(color = CodeBlitzTheme.colors.tertiary)
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Row(
                modifier = Modifier
                    .background(CodeBlitzTheme.colors.background)
                    .height(65.dp)
            ) {
                Spacer(
                    modifier = Modifier.weight(0.4f)
                )
                TransparentIconButtonCodeBlitz(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(vertical = 6.dp),
                    iconId = R.drawable.leaderboard,
                    onClick = { controller.navigate(Routes.Leaderboard.route) }
                )
                Spacer(
                    modifier = Modifier.weight(1.3f)
                )
                TransparentIconButtonCodeBlitz(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(vertical = 6.dp),
                    iconId = R.drawable.settings,
                    onClick = { controller.navigate(Routes.Settings.route) }
                )
                Spacer(
                    modifier = Modifier.weight(0.4f)
                )
            }
        }
    }
}