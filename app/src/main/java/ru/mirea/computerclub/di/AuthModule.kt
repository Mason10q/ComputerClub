package ru.mirea.computerclub.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.mirea.computerclub.domain.AuthUseCase
import ru.mirea.computerclub.domain.AuthUseCaseImpl
import ru.mirea.computerclub.presentation.auth.AuthViewModel

@Module
interface AuthModule {

    @Binds
    fun bindAuthUseCase(useCase: AuthUseCaseImpl): AuthUseCase


    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}