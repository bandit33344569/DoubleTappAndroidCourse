package com.example.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.PrioritySort
import com.example.myapplication.habit.Type
import com.example.myapplication.models.HabitModel
import java.util.Locale

class ListViewModel(private val model: HabitModel, private val habitType: Type): ViewModel() {
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val habits: LiveData<List<Habit>> = mutableHabits

    private var sequence = ""
    private var prioritySort = PrioritySort.None

    init {
        getHabits()
    }

    fun getHabits() {
        applySettings()
    }

    fun setNameAndDescrFilter(sequence: String) {
        this.sequence = sequence.lowercase(Locale.ROOT)
        applySettings()
    }

    fun setPrioritySort(prioritySort: PrioritySort) {
        this.prioritySort = prioritySort
        applySettings()
    }

    private fun applySettings() {
        var filteredHabits = filterByType()
        filteredHabits = filterBySequence(filteredHabits)
        filteredHabits = sortByPriority(filteredHabits)
        mutableHabits.value = filteredHabits

    }

    private fun filterByType(): List<Habit> {
        val allHabits = model.getAll()
        return allHabits.filter{it.type == habitType}
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