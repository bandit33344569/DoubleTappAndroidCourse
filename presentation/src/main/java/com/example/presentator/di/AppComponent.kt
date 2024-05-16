package com.example.presentator.di

import android.content.Context
import com.example.data.di.RepositoryModule
import com.example.domain.useCase.HabitsUseCase
import com.example.presentator.ui.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
    fun inject(listFragment: ListFragment)

    fun getHabitUseCase(): HabitsUseCase

    fun habitCreateSubComponent(): HabitCreateSubComponent.Builder

    fun habitListSubComponent(): HabitListSubComponent.Builder
}