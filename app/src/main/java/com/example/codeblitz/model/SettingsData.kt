package com.example.codeblitz.model

import kotlinx.serialization.Serializable

@Serializable
data class SettingsData (
    val selected_theme: Int?,
    val user_id: String
    )