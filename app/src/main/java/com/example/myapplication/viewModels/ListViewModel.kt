package com.example.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDatabase
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.PrioritySort
import com.example.myapplication.habit.Type
import com.example.myapplication.repository.ApiResponse
import com.example.myapplication.repository.HabitServerRep
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale


class ListViewModel(private val db: HabitDatabase, private val habitType: Type): ViewModel() {
    private val HabitServerRep = HabitServerRep(db.habitDao())
    private lateinit var  allHabits: List<Habit>
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
        allHabits = db.habitDao().getAll().value ?: emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        db.habitDao().getAll().removeObserver(getAllObserver)
    }

    fun setSearchFilter(sequence: String) {
        if (::allHabits.isInitialized) {
            this.sequence = sequence.lowercase(Locale.ROOT)
            applySettings(allHabits)
        }
    }

    fun setPrioritySort(prioritySort: PrioritySort) {
        if (::allHabits.isInitialized) {
            this.prioritySort = prioritySort
            applySettings(allHabits)
        }
    }

    private fun applySettings(habits: List<Habit>) {
        var filteredHabits = filterByType(habits)
        filteredHabits = filterBySequence(filteredHabits)
        filteredHabits = sortByPriority(filteredHabits)
        mutableHabits.postValue(filteredHabits)
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

    fun loadHabit() {
        loadHabitFromServer()
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun loadHabitFromServer(){
        GlobalScope.launch(Dispatchers.IO){
            val apiResponse = HabitServerRep.loadHabitFromServer()
            if (apiResponse is ApiResponse.Error){
                Log.d("HabitServerRepository.loadHabitFromServer", "Error connection")
            }
        }
    }
}