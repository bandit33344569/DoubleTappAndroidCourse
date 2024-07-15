package com.example.data.net.data

import androidx.room.TypeConverter

class HabitDateConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun toString(value: List<Int>): String {
        return value.joinToString(",")
    }
}