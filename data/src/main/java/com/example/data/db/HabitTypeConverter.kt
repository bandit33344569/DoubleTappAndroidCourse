package com.example.data.db

import androidx.room.TypeConverter
import com.example.domain.entity.Priority
import com.example.domain.entity.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitTypeConverter {


    @TypeConverter
    fun toDateList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromDateList(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromPriority(priority: Priority): Int {
        return priority.ordinal
    }

    @TypeConverter
    fun toPriority(priorityIndex: Int): Priority {
        return Priority.entries[priorityIndex]
    }


    @TypeConverter
    fun fromType(type: Type): Int {
        return type.ordinal
    }

    @TypeConverter
    fun toType(typeIndex: Int): Type {
        return Type.entries[typeIndex]
    }


}