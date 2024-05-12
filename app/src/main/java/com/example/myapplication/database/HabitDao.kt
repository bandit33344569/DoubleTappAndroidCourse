package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.PrioritySort
import com.example.myapplication.habit.Type

@Dao
interface HabitDao {
    @Query("SELECT * from habits")
     fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = (:id) LIMIT 1")
     fun getHabitById(id: String): Habit

    @Insert
     fun insert(habit: Habit)

    @Update
     fun update(habit: Habit)

    @Delete
     fun delete(habit: Habit)

    @Query("SELECT * FROM habits WHERE type = :type")
    fun getHabitByType(type: Type): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE type = :type AND (name LIKE '%' || :sequence || '%' OR description LIKE '%' || :sequence || '%') ORDER BY CASE :prioritySort " +
            "WHEN 'HighToLow' THEN priority END DESC, " +
            "CASE :prioritySort WHEN 'LowToHigh' THEN priority END ASC, " +
            "RANDOM()")
    fun searchHabitsByTypeAndSequenceAndPriority(type: Type, sequence: String, prioritySort: PrioritySort): LiveData<List<Habit>>

}