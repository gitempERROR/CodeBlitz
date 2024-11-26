package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme

@Composable
fun EmptyTaskElement(
    controller: NavController,
) {
    Spacer(
        modifier = Modifier.height(20.dp)
    )
    Box(
        modifier = Modifier
            .padding(top = 2.dp)
            .shadow(
                2.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(bottom = 2.dp)
            .fillMaxWidth()
            .height(360.dp)
            .background(
                color = CodeBlitzTheme.colors.primary, shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                controller.navigate(Routes.AddTask.route)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    color = CodeBlitzTheme.colors.background, shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp),
                imageVector = ImageVector.vectorResource(R.drawable.plus),
                contentDescription = "",
                tint = CodeBlitzTheme.colors.secondary
            )
        }
    }
}