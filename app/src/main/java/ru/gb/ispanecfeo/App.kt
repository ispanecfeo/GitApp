package ru.gb.ispanecfeo

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.data.local.room.RetrofitUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.RoomUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.UsersDB
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.utils.NetworkStatus

class App : Application() {

    private val gitHubBaseUrl: String = "https://api.github.com/"

    private val retrofit: Retrofit by lazy {

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

    private val api: GitHubApi by lazy { retrofit.create(GitHubApi::class.java) }
    val userRepoRemote: UserRepo.Remote by lazy { RetrofitUserRepoImpl(api) }

    private val dao by lazy { UsersDB.getDB(applicationContext) }
    val userRepoLocal: UserRepo.Local by lazy { RoomUserRepoImpl(dao!!) }

    private val status: PublishSubject<Boolean> by lazy { PublishSubject.create()}

    val networkStatus: NetworkStatus by lazy { NetworkStatus(applicationContext, status) }

}


val Context.appInstance: App get() = applicationContext as App