package com.example.presentator.di

import android.content.Context
import com.example.data.di.RepositoryModule
import com.example.domain.useCase.HabitsUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun getHabitUseCase(): HabitsUseCase

    fun habitCreateSubComponent(): HabitCreateSubComponent.Builder

    fun habitListSubComponent(): HabitListSubComponent.Builder
}