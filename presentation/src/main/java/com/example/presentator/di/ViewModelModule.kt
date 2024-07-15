package com.example.presentator.di

import com.example.domain.useCase.HabitsUseCase
import com.example.presentator.vm.ListViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideListViewModel(useCase: HabitsUseCase): ListViewModel {
        return ListViewModel(useCase)
    }
}