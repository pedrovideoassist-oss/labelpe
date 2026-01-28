package com.validatech.util

import java.security.SecureRandom

object ShortCodeGenerator {
    private const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
    private val random = SecureRandom()

    fun generate(length: Int = 7): String {
        val builder = StringBuilder()
        repeat(length) {
            builder.append(alphabet[random.nextInt(alphabet.length)])
        }
        return builder.toString()
    }
}
