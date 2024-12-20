package com.example.codeblitz.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

//Класс с данными о днях для команды Select
@Serializable
data class Days(
    val id: Int,
    val day_date: LocalDate,
    val task_count: Int
)