package ru.mirea.computerclub.di

import dagger.Component
import ru.mirea.computerclub.presentation.auth.SignInFragment
import ru.mirea.computerclub.presentation.auth.SignUpFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, NetworkModule::class, AuthModule::class])
interface AppComponent {

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

}