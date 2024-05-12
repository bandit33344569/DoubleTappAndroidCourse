package com.example.myapplication.models

import com.example.myapplication.habit.Habit

data class AddServerHabitBody(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    var doneDates: List<Int> = listOf(),
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
) {

    companion object {
        fun habitToHabitServer(habit: Habit) = AddServerHabitBody(
            color = habit.color,
            count = habit.times,
            description = habit.description,
            title = habit.name,
            type = habit.type.ordinal,
            priority = habit.priority.ordinal,
            date = habit.date,
            frequency = habit.period
        )
    }
}