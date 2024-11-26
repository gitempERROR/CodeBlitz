package com.example.codeblitz.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskSolutionsUpdate (
    val language_id: Int,
    val code: String,
    val end_time: LocalTime?,
)