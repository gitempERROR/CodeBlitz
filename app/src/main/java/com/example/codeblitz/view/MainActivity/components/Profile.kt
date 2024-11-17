package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.ButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TextFieldCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

@Preview
@Composable
fun Profile() {
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
                tint = CodeBlitzTheme.colors.secondary
            )
            Text(
                text = "Профиль",
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
                tint = CodeBlitzTheme.colors.primary
            )
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        TextFieldCodeBlitz(
            modifier = Modifier.padding(horizontal = 35.dp).fillMaxWidth(),
            internalModifier = Modifier.fillMaxWidth().height(35.dp),
            labelGap = 20.dp,
            text = "Имя",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        TextFieldCodeBlitz(
            modifier = Modifier.padding(horizontal = 35.dp).fillMaxWidth(),
            internalModifier = Modifier.fillMaxWidth().height(35.dp),
            labelGap = 20.dp,
            text = "Фамилия",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        TextFieldCodeBlitz(
            modifier = Modifier.padding(horizontal = 35.dp).fillMaxWidth(),
            internalModifier = Modifier.fillMaxWidth().height(35.dp),
            labelGap = 20.dp,
            text = "Имя пользователя",
            paddingValues = PaddingValues(
                start = 10.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            style = CodeBlitzTheme.typography.bodyLarge,
            isSettings = true
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ButtonCodeBlitz(
            text = "Выйти",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 35.dp, end = 35.dp, bottom = 50.dp)
                .height(65.dp)
                .fillMaxWidth()
        )
    }
}