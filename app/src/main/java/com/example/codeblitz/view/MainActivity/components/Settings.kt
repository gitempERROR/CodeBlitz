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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.SettingsViewModel
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import com.example.codeblitz.view.ui.theme.CustomDropDownMenuColors
import com.example.codeblitz.view.ui.theme.IconButtonCodeBlitz
import com.example.codeblitz.view.ui.theme.TransparentIconButtonCodeBlitz

@Composable
fun Settings(controller: NavController, viewModel: SettingsViewModel = hiltViewModel()) {

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
                text = "Настройки",
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
                tint = CodeBlitzTheme.colors.primary,
                onClick = { viewModel.navigateToMain() }
            )
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        Text(
            text = "Выбранная тема",
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
            style = CodeBlitzTheme.typography.bodyLarge,
            color = CodeBlitzTheme.colors.tertiary
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        CustomDropDownMenuColors(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            backgroundColor = CodeBlitzTheme.colors.background,
            options = viewModel.options,
            selectedScheme = viewModel.selectedOption,
            onOptionSelected = { newColorScheme -> viewModel.changeTheme(newColorScheme) }
        )
    }
}