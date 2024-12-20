package com.example.codeblitz.model

import kotlinx.serialization.Serializable

//Данные пользователей
@Serializable
data class UserData(
    val id: String,
    val firstname: String,
    val surname: String,
    val nickname: String,
    val role_id: Int
)