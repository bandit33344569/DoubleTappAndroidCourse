package com.example.data.di

import android.content.Context
import com.example.data.db.HabitDao
import com.example.data.db.HabitDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(database: HabitDatabase): HabitDao {
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): HabitDatabase {
        return HabitDatabase.getInstance(context)
    }

}