package com.example.codeblitz.model

import kotlinx.serialization.Serializable

@Serializable
data class SolutionStatuses(
    val id: Int,
    val status_name: String
)