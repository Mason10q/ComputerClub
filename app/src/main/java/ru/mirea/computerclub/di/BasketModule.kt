package ru.mirea.computerclub.di

import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.mirea.computerclub.domain.BasketUseCase
import ru.mirea.computerclub.domain.BasketUseCaseImpl
import ru.mirea.computerclub.presentation.basket.BasketAdapter
import ru.mirea.computerclub.presentation.basket.BasketViewModel

@Module
class BasketModule {

    @Provides
    fun provideBasketAdapter(picasso: Picasso) = BasketAdapter(picasso)

    @Module
    interface Bind {
        @Binds
        fun bindBasketUseCase(useCase: BasketUseCaseImpl): BasketUseCase

        @Binds
        @IntoMap
        @ViewModelKey(BasketViewModel::class)
        fun bindComputersViewModel(viewModel: BasketViewModel): ViewModel

    }

}