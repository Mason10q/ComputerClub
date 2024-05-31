package ru.mirea.computerclub

import android.app.Application
import ru.mirea.computerclub.di.AndroidModule
import ru.mirea.computerclub.di.AppComponent
import ru.mirea.computerclub.di.ComputerModule
import ru.mirea.computerclub.di.DaggerAppComponent

class App: Application() {

    var appComponent: AppComponent? = null
        private set


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .androidModule(AndroidModule(applicationContext))
            .computerModule(ComputerModule(applicationContext))
            .build()
    }
}