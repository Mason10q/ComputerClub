package ru.mirea.computerclub.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.mirea.computerclub.core.ViewModelFactory

@Module
interface CoreModule {

    @Binds
    fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

}