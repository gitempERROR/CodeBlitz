package com.example.codeblitz.view.MainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codeblitz.R
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme

@Composable
fun TaskElement(
    controller: NavController,
    title: String = "Empty",
    desc: String = "Empty",
    status: String = "not started",
    id: Int = 0
) {

    val icons = mapOf(
        "not started" to R.drawable.start,
        "started" to R.drawable.ellipsis,
        "solved" to R.drawable.finished,
        "approved" to R.drawable.check,
        "denied" to R.drawable.cross
    )

    val backgroundColors = mapOf(
        "not started" to CodeBlitzTheme.colors.primary,
        "started" to CodeBlitzTheme.colors.tertiary,
        "solved" to CodeBlitzTheme.colors.tertiary,
        "approved" to CodeBlitzTheme.colors.secondary,
        "denied" to CodeBlitzTheme.colors.secondaryContainer
    )

    val iconColors = mapOf(
        "not started" to CodeBlitzTheme.colors.secondary,
        "started" to CodeBlitzTheme.colors.secondary,
        "solved" to CodeBlitzTheme.colors.tertiary,
        "approved" to CodeBlitzTheme.colors.secondary,
        "denied" to CodeBlitzTheme.colors.secondaryContainer
    )

    val textColors = mapOf(
        "not started" to CodeBlitzTheme.colors.tertiary,
        "started" to CodeBlitzTheme.colors.tertiary,
        "solved" to CodeBlitzTheme.colors.tertiary,
        "approved" to CodeBlitzTheme.colors.secondary,
        "denied" to CodeBlitzTheme.colors.secondaryContainer
    )

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
                color = backgroundColors[status]!!, shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                controller.navigate(
                    "TaskDesc"
                    + "/${title}"
                    + "/${desc}"
                    + "/${status}"
                    + "/${id}"
                )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp)
            ) {
                Text(
                    text = title,
                    style = CodeBlitzTheme.typography.bodyLarge,
                    color = textColors[status]!!
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )
                Text(
                    text = desc,
                    style = CodeBlitzTheme.typography.titleSmall,
                    color = textColors[status]!!,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(icons[status]!!),
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .size(45.dp),
                    tint = iconColors[status]!!,
                    contentDescription = ""
                )
            }
        }
    }
}