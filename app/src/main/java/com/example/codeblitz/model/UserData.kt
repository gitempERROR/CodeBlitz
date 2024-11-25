package com.example.codeblitz.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData (
    val id: String,
    val firstname: String,
    val surname: String,
    val nickname: String,
    val role_id: Int
)