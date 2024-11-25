package com.example.codeblitz.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRoles (
    val id: Int,
    val role_name: String
)