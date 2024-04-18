package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.PrioritySort
import com.example.myapplication.habit.Type

import java.util.Locale

class ListViewModel(private val db: HabitDatabase, private val habitType: Type): ViewModel() {
    private lateinit var allHabits: List<Habit>
    private val getAllObserver = Observer<List<Habit>> { habits ->
        allHabits = habits
        applySettings(habits)
    }
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val habits: LiveData<List<Habit>> = mutableHabits

    private var sequence = ""
    private var prioritySort = PrioritySort.None

    init {
        db.habitDao().getAll().observeForever(getAllObserver)
    }

    override fun onCleared() {
        super.onCleared()
        db.habitDao().getAll().removeObserver(getAllObserver)
    }

    fun setSearchFilter(sequence: String) {
        this.sequence = sequence.lowercase(Locale.ROOT)
        applySettings(allHabits)
    }

    fun setPrioritySort(prioritySort: PrioritySort) {
        this.prioritySort = prioritySort
        applySettings(allHabits)
    }

    private fun applySettings(habits: List<Habit>) {
        var filteredHabits = filterByType(habits)
        filteredHabits = filterBySequence(filteredHabits)
        filteredHabits = sortByPriority(filteredHabits)
        mutableHabits.value = filteredHabits
    }

    private fun filterByType(habits: List<Habit>): List<Habit> {
        return habits.filter{it.type == habitType}
    }

    private fun filterBySequence(habits: List<Habit>): List<Habit> {
        if (this.sequence != "") {
            return habits.filter {
                it.name.lowercase(Locale.ROOT).contains(this.sequence) or
                        it.description.lowercase(Locale.ROOT).contains(this.sequence)
            }
        }
        return habits
    }

    private fun sortByPriority(habits: List<Habit>): List<Habit> {
        if (prioritySort == PrioritySort.HighToLow) {
            return habits.sortedByDescending {it.priority}
        }
        if (prioritySort == PrioritySort.LowToHigh) {
            return habits.sortedBy {it.priority}
        }

        return habits
    }
}