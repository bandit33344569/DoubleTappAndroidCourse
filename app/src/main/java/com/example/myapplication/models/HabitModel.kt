package com.example.myapplication.models

import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Priority
import com.example.myapplication.habit.Type

class HabitModel private constructor() {
    companion object {
        private lateinit var instance: HabitModel

        fun getInstance(): HabitModel {
            if (!::instance.isInitialized) {
                instance = HabitModel()
            }
            return instance
        }
    }

    private var habits = mutableListOf(
        Habit("Сходить в тренажерный зал", "", Priority.High, Type.Good, 1, 3),
        Habit("Прибраться", "генеральная уборка", Priority.Medium, Type.Good, 1, 14),
        Habit("Сделать домашку", "", Priority.Low, Type.Bad, 1, 2),
        Habit("Выпить пивка", "с друзьями", Priority.Medium, Type.Bad, 1, 7),
        Habit("Купить сигареты", "чапман ред", Priority.High, Type.Bad, 1, 3)
    )

    fun add(habit: Habit) {
        habits.add(habit)
    }

    fun edit(habit: Habit) {
        val oldHabit = habits.find{it.Id == habit.Id}
        val oldHabitPosition = habits.indexOf(oldHabit)
        habits[oldHabitPosition] = habit
    }

    fun getAll(): MutableList<Habit> {
        return habits
    }
}