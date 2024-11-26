package com.example.codeblitz.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskSolutionsInsert (
    val user_id: String,
    val task_id: Int,
    val language_id: Int,
    val code: String,
    val current_status: Int,
    val start_time: LocalTime,
    val end_time: LocalTime?,
)