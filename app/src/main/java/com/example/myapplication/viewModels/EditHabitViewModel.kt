package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import com.example.myapplication.habit.Habit
import com.example.myapplication.models.HabitModel
import java.util.UUID

class EditHabitViewModel(private val model: HabitModel): ViewModel() {
    fun addHabit(habit: Habit) {
        model.add(habit)
    }

    fun editHabit(habit: Habit) {
        model.edit(habit)
    }

    fun getHabitById(id: UUID): Habit? {
        return model.getAll().find {it.Id == id}
    }
}