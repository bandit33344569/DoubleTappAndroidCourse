package com.example.presentator.di

import com.example.presentator.ui.ListFragment
import dagger.Subcomponent

@Subcomponent
interface HabitListFactorySubComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): HabitListFactorySubComponent
    }

    fun inject(habitListFragment: ListFragment)

}