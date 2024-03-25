package com.example.myapplication

import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Type

object HabitsManager {
    private val habitsList = mutableListOf<Habit>()
    private val goodhabitsList = mutableListOf<Habit>()
    private val badhabitsList = mutableListOf<Habit>()

    fun addHabit(habit: Habit) {
        habitsList.add(habit)
        if (habit.type == Type.Good){
            goodhabitsList.add(habit)
        }
        else badhabitsList.add(habit)
    }

    fun removeHabit(habit: Habit) {
        habitsList.remove(habit)
    }

    fun getHabits(): List<Habit> {
        return habitsList
    }

    fun getBadHabits(): List<Habit> {
        return badhabitsList
    }

    fun getGoodHabits(): List<Habit> {
        return goodhabitsList
    }

    private fun filterHabitsByType(type: Type): List<Habit> {
        return habitsList.filter {
            if (type == Type.Good) {
                it.type == Type.Good
            } else {
                it.type == Type.Bad
            }
        }
    }
}