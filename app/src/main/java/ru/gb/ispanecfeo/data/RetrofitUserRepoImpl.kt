package ru.gb.ispanecfeo.data

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo

private const val gitHubBaseUrl : String = "https://api.github.com/"

class RetrofitUserRepoImpl:UserRepo {

    private val server = Retrofit.Builder()
        .baseUrl(gitHubBaseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(GitHubApi::class.java)


    override fun getUsers(callback: Callback<List<UserEntity>>) {
        server.getGitHubUsers().enqueue(callback)
    }

    override fun getInfoUser(login: String, callback: Callback<UserInfoEntity>) {
        server.getGitHubUserInfo(login).enqueue(callback)
    }


}