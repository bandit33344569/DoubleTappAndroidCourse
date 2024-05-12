package com.example.myapplication.habit

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "habits")
@TypeConverters(PriorityConverter::class, HabitTypeConverter::class)
class Habit(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "priority")
    val priority: Priority,
    @ColumnInfo(name = "type")
    val type: Type,
    @ColumnInfo(name = "times")
    val times: Int,
    @ColumnInfo(name = "period")
    val period: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var Id: String = "",
    @ColumnInfo(name = "date")
    val date: Int = -1,
    @ColumnInfo(name = "color")
    val color: Int = 0

) : Parcelable {
}

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): Int {
        return priority.ordinal
    }

    @TypeConverter
    fun toPriority(priorityIndex: Int): Priority {
        return Priority.entries[priorityIndex]
    }
}

class HabitTypeConverter {
    @TypeConverter
    fun fromType(type: Type): Int {
        return type.ordinal
    }

    @TypeConverter
    fun toType(typeIndex: Int): Type {
        return Type.entries[typeIndex]
    }
}