package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Класс с данными для получения и передачи заданий
@Serializable
data class Tasks(
    val id: Int,
    val day_id: Int,
    val day_task_id: Int,
    val task_description: String,
    var task_status: String = "not started"
)