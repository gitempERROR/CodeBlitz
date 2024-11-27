package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Класс с данными о языках программирования
@Serializable
data class Languages(
    val id: Int,
    val language_name: String
)