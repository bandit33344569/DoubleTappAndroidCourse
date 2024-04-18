package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit

class EditHabitViewModel(private val db: HabitDatabase): ViewModel() {
    fun addHabit(habit: Habit) {
        db.habitDao().insert(habit)
    }

    fun editHabit(habit: Habit) {
        db.habitDao().update(habit)
    }

    fun getHabitById(id: Int): Habit {
        return db.habitDao().getHabitById(id)
    }
}