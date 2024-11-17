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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme

@Preview
@Composable
fun TaskElement(
    title: String = "Empty", desc: String = "Empty", statusIcon: Int = R.drawable.start
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    color = CodeBlitzTheme.colors.background, shape = RoundedCornerShape(10.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp)
            ) {
                Text(
                    text = title,
                    style = CodeBlitzTheme.typography.bodyLarge,
                    color = CodeBlitzTheme.colors.tertiary
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
                Text(
                    text = title,
                    style = CodeBlitzTheme.typography.bodyMedium,
                    color = CodeBlitzTheme.colors.tertiary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(statusIcon),
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .size(45.dp),
                    tint = CodeBlitzTheme.colors.secondary,
                    contentDescription = ""
                )
            }
        }
    }
}