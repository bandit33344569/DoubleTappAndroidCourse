package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.entity.Type
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * from HabitEntity")
    fun getAll(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM HabitEntity WHERE id = (:id) LIMIT 1")
    fun getHabitById(id: String): HabitEntity

    @Insert
    fun insert(habit: HabitEntity)

    @Update
    fun update(habit: HabitEntity)

    @Delete
    fun delete(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE type = :type")
    fun getHabitByType(type: Type): LiveData<List<HabitEntity>>
}