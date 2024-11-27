package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Класс с данными о статусах заданий
@Serializable
data class SolutionStatuses(
    val id: Int,
    val status_name: String
)