package ru.gb.ispanecfeo

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.gb.ispanecfeo.data.RetrofitUserRepoImpl
import ru.gb.ispanecfeo.domain.repos.UserRepo

class App : Application() {
    val userRepo: UserRepo by lazy { RetrofitUserRepoImpl() }
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext() as App