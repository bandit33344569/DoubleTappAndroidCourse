package com.example.myapplication.habit

enum class Priority(val value: String) {
    Low("Низкий"),
    Medium("Средний"),
    High("Высокий");
    companion object {
        fun createByPriority(ordinal: Int): Priority {
            return when (ordinal) {
                1 -> Low
                2 -> Medium
                else -> High
            }
        }
    }
}