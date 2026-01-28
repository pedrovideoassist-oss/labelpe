package com.validatech.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun formatDate(dateTime: LocalDateTime): String = dateTime.format(dateFormatter)

    fun formatDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    fun nowWithOptionalTime(includeTime: Boolean): LocalDateTime {
        val now = LocalDateTime.now()
        return if (includeTime) now else LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)
    }
}
