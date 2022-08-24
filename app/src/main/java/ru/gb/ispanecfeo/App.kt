package ru.gb.ispanecfeo

import android.app.Application
import ru.gb.ispanecfeo.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appModule.setup(this)
    }
}

inline fun <reified T> inject(qualifier: String? = null) = lazy {
    appModule.get<T>(qualifier)
}
