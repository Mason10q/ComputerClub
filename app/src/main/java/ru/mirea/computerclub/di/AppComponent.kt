package ru.mirea.computerclub.di

import dagger.Component
import ru.mirea.computerclub.presentation.auth.SignInFragment
import ru.mirea.computerclub.presentation.auth.SignUpFragment
import ru.mirea.computerclub.presentation.basket.BasketFragment
import ru.mirea.computerclub.presentation.computers.ComputerDialog
import ru.mirea.computerclub.presentation.computers.ComputersFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CoreModule::class,
    NetworkModule::class,
    AuthModule::class,
    ComputerModule::class,
    ComputerModule.Bind::class,
    AndroidModule::class,
    BasketModule::class,
    BasketModule.Bind::class
 ])
interface AppComponent {

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: ComputersFragment)
    fun inject(fragment: BasketFragment)

    fun inject(fragment: ComputerDialog)


    @Component.Builder
    interface Builder {

        fun androidModule(module: AndroidModule): Builder

        fun computerModule(module: ComputerModule): Builder

        fun build(): AppComponent

    }

}