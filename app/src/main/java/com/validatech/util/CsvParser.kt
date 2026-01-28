package com.validatech.util

object CsvParser {
    fun parse(text: String): List<Map<String, String>> {
        val lines = text.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyList()
        val headers = lines.first().split(",").map { it.trim() }
        return lines.drop(1).map { line ->
            val values = line.split(",")
            headers.mapIndexed { index, header ->
                header to values.getOrNull(index)?.trim().orEmpty()
            }.toMap()
        }
    }
}
