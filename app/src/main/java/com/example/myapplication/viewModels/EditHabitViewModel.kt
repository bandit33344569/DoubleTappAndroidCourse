package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class EditHabitViewModel(private val db: HabitDatabase) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    fun addHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                db.habitDao().insert(habit)
            }
        }
    }

    fun editHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                db.habitDao().update(habit)
            }
        }
    }

    fun deleteHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) {
                db.habitDao().delete(habit)
            }
        }
    }

    fun getHabitById(id: Int): Habit {
        return db.habitDao().getHabitById(id)
    }
}
