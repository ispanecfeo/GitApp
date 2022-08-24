package ru.gb.ispanecfeo.di

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.depinjlib.module
import ru.gb.ispanecfeo.App
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.data.local.room.RetrofitUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.RoomUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.UserDao
import ru.gb.ispanecfeo.data.local.room.UsersDB
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.NetworkStatus
import kotlin.math.sin

@SuppressLint("StaticFieldLeak")
val appModule = module {

     val gitHubBaseUrl = "https://api.github.com/"

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(gitHubBaseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single<GitHubApi> {
        get<Retrofit>().create(GitHubApi::class.java)
    }

    single<UserRepo.Remote> {
        RetrofitUserRepoImpl(get())
    }

    single {
        UsersDB.getDB(androidContext)
    }

    single<UserRepo.Local> {
        RoomUserRepoImpl(get())
    }

    single<PublishSubject<Boolean>> {
        PublishSubject.create()
    }

    single {
        NetworkStatus(androidContext, get())
    }

}