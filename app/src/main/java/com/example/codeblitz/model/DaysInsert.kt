package com.example.codeblitz.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DaysInsert(
    val day_date: LocalDate,
    val task_count: Int
)