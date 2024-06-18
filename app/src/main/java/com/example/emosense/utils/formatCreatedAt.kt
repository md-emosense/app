package com.example.emosense.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatCreatedAt(createdAt: String): String {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(createdAt, formatter)
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val duration = Duration.between(zonedDateTime, now)

        when {
            duration.toMinutes() < 60 -> "${duration.toMinutes()} menit yang lalu"
            duration.toHours() < 24 -> "${duration.toHours()} jam yang lalu"
            duration.toDays() == 1L -> "1 hari yang lalu"
            duration.toDays() <= 2L -> "${duration.toDays()} hari yang lalu"
            else -> zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("id", "ID")))
        }
    } catch (e: DateTimeParseException) {
        "Tanggal tidak valid"
    }
}