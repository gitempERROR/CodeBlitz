package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Класс с данными для вставки заданий
@Serializable
data class TasksInsert(
    val day_id: Int,
    val task_description: String
)