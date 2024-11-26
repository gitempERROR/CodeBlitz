package com.example.codeblitz.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskSolutions (
    val id: Int,
    val user_id: String,
    val task_id: Int,
    val language_id: Languages,
    val code: String,
    val current_status: SolutionStatuses,
    val start_time: LocalTime,
    val end_time: LocalTime,
    )