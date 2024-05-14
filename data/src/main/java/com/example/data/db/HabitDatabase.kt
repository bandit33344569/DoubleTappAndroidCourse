package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HabitEntity::class], version = 2)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        private lateinit var instance: HabitDatabase

        fun getInstance(context: Context): HabitDatabase {
            if (!Companion::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context,
                    HabitDatabase::class.java,
                    "habitsDB")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

}