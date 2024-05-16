package com.example.presentator.di

import com.example.presentator.ui.ListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface HabitListSubComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): HabitListSubComponent
    }

    fun inject(habitListFragment: ListFragment)

}