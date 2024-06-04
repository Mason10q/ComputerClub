package ru.mirea.computerclub.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.network.dtos.PurchaseDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.domain.AuthUseCase
import ru.mirea.computerclub.domain.AuthUseCaseImpl
import ru.mirea.computerclub.domain.entities.Purchase
import ru.mirea.computerclub.domain.entities.User
import ru.mirea.computerclub.domain.mappers.PurchaseMapper
import ru.mirea.computerclub.domain.mappers.UserMapper
import ru.mirea.computerclub.presentation.auth.AuthViewModel
import ru.mirea.computerclub.presentation.profile.ProfileViewModel

@Module
interface AuthModule {

    @Binds
    fun bindAuthUseCase(useCase: AuthUseCaseImpl): AuthUseCase

    @Binds
    fun bindUserMapper(mapper: UserMapper): Mapper<UserDto, User>

    @Binds
    fun bindPurchaseMapper(mapper: PurchaseMapper): Mapper<PurchaseDto, Purchase>


    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}