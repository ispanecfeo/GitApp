package ru.gb.ispanecfeo.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.data.local.room.RetrofitUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.RoomUserRepoImpl
import ru.gb.ispanecfeo.data.local.room.UserDao
import ru.gb.ispanecfeo.data.local.room.UsersDB
import ru.gb.ispanecfeo.domain.repos.UserRepo
import ru.gb.ispanecfeo.ui.users.UserInfoViewModel
import ru.gb.ispanecfeo.ui.users.UsersViewModel
import ru.gb.ispanecfeo.ui.utils.NetworkStatus
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providesBaseUrl() : String {
        return "https://api.github.com/"
    }

    @Provides
    @Singleton
    fun providesRetrofit(baseUrl:String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesGithubApi(retrofit: Retrofit) : GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepoRemote(api: GitHubApi) : UserRepo.Remote {
        return RetrofitUserRepoImpl(api)
    }

    @Provides
    @Singleton
    fun providesPublishSubject() : PublishSubject<Boolean> {
        return PublishSubject.create()
    }

    @Provides
    @Singleton
    fun providesNetworkStatus(context: Context, subject: PublishSubject<Boolean>): NetworkStatus {
        return NetworkStatus(context, subject)
    }

    @Provides
    @Singleton
    fun providesUserDao(context: Context) : UserDao {
        return UsersDB.getDB(context)!!
    }

    @Provides
    @Singleton
    fun providesUserRepoLocal(dao: UserDao): UserRepo.Local {
        return RoomUserRepoImpl(dao)
    }

    @Provides
    @Singleton
    fun providesUserViewModel(remote: UserRepo.Remote, local : UserRepo.Local):UsersViewModel {
        return UsersViewModel(remote, local)
    }

    @Provides
    @Singleton
    fun providesUserInfoViewModel(remote: UserRepo.Remote, local : UserRepo.Local) : UserInfoViewModel {
        return UserInfoViewModel(remote, local)
    }

}