package com.example.data.di

import com.example.data.db.HabitDao
import com.example.data.net.HabitServerApi
import com.example.data.rep.HabitServerRepository
import com.example.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModel::class, DatabaseModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(habitDao: HabitDao, habitAPI: HabitServerApi): IRepository {
        return HabitServerRepository(habitDao, habitAPI)
    }

}