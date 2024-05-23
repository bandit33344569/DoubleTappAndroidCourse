package com.example.domain.entity

enum class Priority(val value: String) {
    Low("Низкий"),
    Medium("Средний"),
    High("Высокий");
    companion object {
        fun createByPriority(ordinal: Int): Priority {
            return when (ordinal) {
                0 -> Low
                1 -> Medium
                else -> High
            }
        }
    }
}