package com.comppot.mindsnack.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object DateUtils {
    private const val DATE_FORMAT = "d MMMM"

    fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.forLanguageTag("uk"))
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000
        return formatter.format(calendar.time)
    }

    fun getTimeDifference(timestamp: Long): String {
        val currentTime = System.currentTimeMillis() / 1000
        val diffInSeconds = currentTime - timestamp

        val duration = diffInSeconds.toDuration(DurationUnit.SECONDS)

        return when {
            duration.inWholeSeconds < 60 -> "${duration.inWholeSeconds} с"
            duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes} хв"
            duration.inWholeHours < 24 -> "${duration.inWholeHours} год"
            duration.inWholeDays < 7 -> "${duration.inWholeDays} д"
            else -> "${duration.inWholeDays / 7} тиж"
        }
    }
}