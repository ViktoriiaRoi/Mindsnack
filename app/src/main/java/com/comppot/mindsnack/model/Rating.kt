package com.comppot.mindsnack.model

enum class Rating(val value: Int, val emoji: String) {
    VERY_BAD(-2, "\uD83D\uDE30"), // 😰
    BAD(-1, "\uD83D\uDE12"), // 😒
    OKAY(0, "\uD83D\uDE10"), // 😐
    GOOD(1, "\uD83D\uDE0A"), // 😊
    VERY_GOOD(2, "\uD83D\uDE0D"); // 😍

    companion object {
        fun fromValue(value: Int) = Rating.entries.find { it.value == value } ?: OKAY
    }
}
