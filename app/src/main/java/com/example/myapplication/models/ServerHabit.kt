package com.example.myapplication.models

import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Priority
import com.example.myapplication.habit.Type

data class ServerHabit(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    var doneDates: List<Int> = listOf(),
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
) {

    companion object {
        fun habitToHabitServer(habit: Habit) = ServerHabit(
            color = habit.color,
            count = habit.times,
            description = habit.description,
            title = habit.name,
            type = habit.type.ordinal,
            priority = habit.priority.ordinal,
            date = habit.date,
            frequency = habit.period,
            uid = habit.Id,
        )
    }

    fun toHabit() = Habit(
        name = title,
        description = description,
        priority = Priority.createByPriority(priority),
        color = color,
        times = count,
        type = Type.createByType(type),
        date = date,
        Id = uid,
        period = frequency
    )
}