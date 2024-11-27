package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Темы с цветами в формате строк
@Serializable
data class Themes(
    val id: Int,
    val theme_name: String,
    val color_1: String,
    val color_2: String,
    val color_3: String,
    val color_4: String,
    val color_5: String,
    val color_6: String,
)