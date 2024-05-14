package com.example.domain.useCase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.domain.entity.Habit
import com.example.domain.net.ApiResponse
import com.example.domain.repository.IRepository
import javax.inject.Inject

class HabitsUseCase @Inject constructor(
    private val repository: IRepository
) {

    val habits: LiveData<List<Habit>> = repository.getAllHabit().asLiveData()

    suspend fun loadHabitFromServer(){
        val apiResponse = repository.loadHabitFromServer()
        if (apiResponse is ApiResponse.Error){
            Log.d("HabitServerRepository.loadHabitFromServer", "Error connection")
        }
    }

    suspend fun createHabit(habit: Habit){
        val apiResponse = repository.createHabit(habit)
        if (apiResponse is ApiResponse.Error){
            Log.d("HabitServerRepository.createHabit", "Error connection")
        }
    }

    suspend fun removeHabit(habit: Habit){
        val apiResponse = repository.removeHabit(habit)
        if (apiResponse is ApiResponse.Error){
            Log.d("HabitServerRepository.removeHabit", "Error connection")
        }
    }

    suspend fun editHabit(habit: Habit){
        val apiResponse = repository.editHabit(habit)
        if (apiResponse is ApiResponse.Error) {
            Log.d("HabitServerRep.createHabit", "Error connection")
        }
    }

    fun getHabitById(nameId: String): Habit?{
        return repository.getHabitById(nameId)
    }


    private fun getNumberRemainingExecutions(habit: Habit): Int {
        val countBeDone = habit.doneDates.count()
        return habit.times - countBeDone
    }

}
