package ru.gb.ispanecfeo.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gb.ispanecfeo.ui.userinfo.UserInfoActivity
import ru.gb.ispanecfeo.ui.users.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }


    fun inject(mainActivity: MainActivity)
    fun inject(infoActivity: UserInfoActivity)
}