package com.example.codeblitz.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskSolutions (
    val id: Int,
    val user_id: UserData,
    val task_id: Int,
    val language_id: Languages,
    val code: String,
    val current_status: SolutionStatuses,
    val start_time: LocalTime?,
    val end_time: LocalTime?,
    var spent_time: Float = 0f,
    var place: Int = 0,
    var task_title: String = "Empty",
    var task_desc: String = "Empty",
    var date: String = "Empty"
    )