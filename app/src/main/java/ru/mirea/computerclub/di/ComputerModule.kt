package ru.mirea.computerclub.di

import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.domain.ComputerUseCase
import ru.mirea.computerclub.domain.ComputerUseCaseImpl
import ru.mirea.computerclub.domain.entities.Computer
import ru.mirea.computerclub.domain.mappers.ComputerMapper
import ru.mirea.computerclub.presentation.computers.ComputersAdapter
import ru.mirea.computerclub.presentation.computers.ComputersViewModel

@Module
class ComputerModule {

    @Provides
    fun provideComputerAdapter(picasso: Picasso) = ComputersAdapter(picasso)

    @Module
    interface Bind {
        @Binds
        fun bindComputerUseCase(useCase: ComputerUseCaseImpl): ComputerUseCase

        @Binds
        @IntoMap
        @ViewModelKey(ComputersViewModel::class)
        fun bindComputersViewModel(viewModel: ComputersViewModel): ViewModel


        @Binds
        fun bindComputerMapper(mapper: ComputerMapper): Mapper<ComputerDto, Computer>

    }
}