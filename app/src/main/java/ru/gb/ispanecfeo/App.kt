package ru.gb.ispanecfeo

import android.app.Application
import ru.gb.ispanecfeo.di.AppComponent
import ru.gb.ispanecfeo.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}