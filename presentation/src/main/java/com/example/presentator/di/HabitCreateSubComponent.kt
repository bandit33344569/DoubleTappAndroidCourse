package com.example.presentator.di

import com.example.presentator.ui.EditHabitFragment
import dagger.Subcomponent

@Subcomponent
interface HabitCreateSubComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): HabitCreateSubComponent
    }

    fun inject(habitEditFragment: EditHabitFragment)
}