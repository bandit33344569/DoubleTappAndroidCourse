package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.habit.Habit

@Dao
interface HabitDao {
    @Query("SELECT * from habits")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = (:id) LIMIT 1")
    fun getHabitById(id: Int): Habit

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)
}