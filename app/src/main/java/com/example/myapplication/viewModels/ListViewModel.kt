package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.PrioritySort
import com.example.myapplication.habit.Type
import java.util.Locale


class ListViewModel(private val db: HabitDatabase, private var habitType: Type): ViewModel() {
    private val habitsObserver = Observer<List<Habit>> { habits ->
        mutableHabits.value = habits
    }
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val habits: LiveData<List<Habit>> = mutableHabits

    private var sequence = ""
    private var prioritySort = PrioritySort.None

    init {
        applySettings()
    }

    override fun onCleared() {
        super.onCleared()
        db.habitDao().searchHabitsByTypeAndSequenceAndPriority(habitType, sequence, prioritySort).removeObserver(habitsObserver)
    }

    fun setHabitType(type: Type) {
        this.habitType = type
        applySettings()
    }

    fun setSearchFilter(sequence: String) {
        this.sequence = sequence.lowercase(Locale.ROOT)
        applySettings()
    }

    fun setPrioritySort(prioritySort: PrioritySort) {
        this.prioritySort = prioritySort
        applySettings()
    }

    private fun applySettings() {
        val type = habitType
        val sequence = this.sequence
        val prioritySort = this.prioritySort
        db.habitDao().searchHabitsByTypeAndSequenceAndPriority(type, sequence, prioritySort)
            .observeForever(habitsObserver)
    }

}