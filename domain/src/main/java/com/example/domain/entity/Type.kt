package com.example.domain.entity

enum class Type(val value: String) {
    Good("Хорошая"),
    Bad("Плохая");

    companion object {
        fun createByType(ordinal: Int): Type {
            return when (ordinal) {
                1 -> Bad
                else -> Good
            }
        }
    }
}