package com.example.presentator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Habit
import com.example.domain.entity.Type
import com.example.domain.useCase.HabitsUseCase
import com.example.presentator.enums.PrioritySort
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class ListViewModel @Inject constructor(private val useCase: HabitsUseCase) : ViewModel() {
    private lateinit var habitType: Type
    private var allHabitsLiveData: LiveData<List<Habit>> = useCase.getAllHabits()
    private var allHabits = allHabitsLiveData.value

    private val getAllObserver = Observer<List<Habit>> { habits ->
        allHabits = habits
        applySettings(habits)
    }


    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val habits: LiveData<List<Habit>> = mutableHabits

    private var sequence = ""
    private var prioritySort = PrioritySort.None

    init {
        useCase.getAllHabits().observeForever(getAllObserver)
    }

    fun setType(type: Type) {
        habitType = type
        allHabits?.let { applySettings(it) }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.getAllHabits().removeObserver(getAllObserver)
    }

    fun setSearchFilter(sequence: String) {
        this.sequence = sequence.lowercase(Locale.ROOT)
        allHabits?.let { applySettings(it) }
    }

    fun setPrioritySort(prioritySort: PrioritySort) {
        this.prioritySort = prioritySort
        allHabits?.let { applySettings(it) }
    }

    private fun applySettings(habits: List<Habit>) {
        var filteredHabits = filterByType(habits)
        filteredHabits = filterBySequence(filteredHabits)
        filteredHabits = sortByPriority(filteredHabits)
        mutableHabits.postValue(filteredHabits)
    }

    private fun filterByType(habits: List<Habit>): List<Habit> {
        return habits.filter { it.type == habitType }
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
            return habits.sortedByDescending { it.priority }
        }
        if (prioritySort == PrioritySort.LowToHigh) {
            return habits.sortedBy { it.priority }
        }

        return habits
    }

    fun loadHabit() {
        viewModelScope.launch {
            useCase.loadHabitFromServer()
        }
    }

    fun doneHabit(habit: Habit) {
        viewModelScope.launch {
            useCase.doneHabit(habit)
            allHabits?.let { applySettings(it) }
        }
    }
}