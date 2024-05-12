package com.example.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit
import com.example.myapplication.repository.ApiResponse
import com.example.myapplication.repository.HabitServerRep
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class EditHabitViewModel(private val db: HabitDatabase) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val HabitServerRep = HabitServerRep(db.habitDao())
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }


    fun addHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                val apiResponse = HabitServerRep.addHabit(habit)
                if (apiResponse is ApiResponse.Error) {
                    Log.d("HabitServerRepository.createHabit", "Error connection")
                }
            }
        }
    }

    fun editHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                val apiResponse = HabitServerRep.editHabit(habit)
                if (apiResponse is ApiResponse.Error) {
                    Log.d("HabitServerRep.createHabit", "Error connection")
                }
            }
        }
    }

    fun deleteHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                val apiResponse = HabitServerRep.removeHabit(habit)
                if (apiResponse is ApiResponse.Error){
                    Log.d("HabitServerRep.removeHabit", "Error connection")
                }
            }
        }
    }

    fun getHabitById(id: String): Habit {
        return db.habitDao().getHabitById(id)
    }
}
