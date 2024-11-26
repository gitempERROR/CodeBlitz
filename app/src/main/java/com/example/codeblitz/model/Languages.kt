package com.example.codeblitz.model

import kotlinx.serialization.Serializable

@Serializable
data class Languages(
    val id: Int,
    val language_name: String
)