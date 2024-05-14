package com.example.presentator.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Habit
import com.example.domain.useCase.HabitsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditHabitViewModel @Inject constructor(
    private val useCase: HabitsUseCase
): ViewModel() {

     fun addHabit(habit: Habit){
        viewModelScope.launch {
            useCase.createHabit(habit)
        }
    }

     fun editHabit(habit: Habit){
        viewModelScope.launch {
            useCase.editHabit(habit)
        }
    }

     fun deleteHabit(habit: Habit){
        viewModelScope.launch {
            useCase.removeHabit(habit)
        }
    }

    fun getHabitById(id: String): Habit? {
        return useCase.getHabitById(id)
    }
}