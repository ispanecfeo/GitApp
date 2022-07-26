package ru.gb.ispanecfeo.data.local.room

import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo


private const val gitHubBaseUrl: String = "https://api.github.com/"

class RetrofitUserRepoImpl : UserRepo.Remote {

    private val server = Retrofit.Builder()
        .baseUrl(gitHubBaseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(GitHubApi::class.java)


    override fun getUsers(): Single<List<UserEntity>> = server.getGitHubUsers()
    override fun getInfoUser(login: String): Single<UserInfoEntity> = server.getGitHubUserInfo(login)

}