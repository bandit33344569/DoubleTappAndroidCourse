package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.net.data.ServerHabit
import com.example.domain.entity.Habit
import com.example.domain.entity.Priority
import com.example.domain.entity.Type
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(HabitTypeConverter::class)
data class HabitEntity (
    var name: String,
    var description: String,
    var priority: Int,
    var type: Int,
    var count: Int,
    var period: Int,
    var color: Int,
    @PrimaryKey
    var id: String,
    var date: Int,
    @SerializedName("done_dates")
    var doneDates: List<Int>
) {
    companion object {
        fun fromHabit(habit: Habit): HabitEntity {
            return HabitEntity(
                id = habit.id,
                date = habit.date,
                count = habit.times,
                color = habit.color,
                period = habit.period,
                type = habit.type.ordinal,
                priority = habit.priority.ordinal,
                description = habit.description,
                name = habit.name,
                doneDates = habit.doneDates
            )
        }

        fun fromServerHabit(habit: ServerHabit): HabitEntity {
            return HabitEntity(
                id = habit.uid,
                date = habit.date,
                count = habit.count,
                color = habit.color,
                period = habit.frequency,
                type = habit.type,
                priority = habit.priority,
                description = habit.description,
                name = habit.title,
                doneDates = habit.doneDates
            )
        }

    }


    fun toHabit() = Habit (
        name = name,
        description = description,
        priority = Priority.createByPriority(priority),
        color = color,
        times = count,
        type = Type.createByType(type),
        date = date,
        id = id,
        period = period,
        doneDates = doneDates
    )
}