package com.example.codeblitz.domain.utils

import android.icu.util.Calendar
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentTime():LocalTime {
    val date = Calendar.getInstance().time
    val instant = Instant.fromEpochMilliseconds(date.time)
    val zonedDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    return zonedDateTime.time
}