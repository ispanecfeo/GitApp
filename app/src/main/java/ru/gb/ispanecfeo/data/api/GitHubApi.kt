package ru.gb.ispanecfeo.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

interface GitHubApi {

    @GET("users")
    fun getGitHubUsers() : Call<List<UserEntity>>

    @GET("users/{login}")
    fun getGitHubUserInfo(@Path("login") login: String): Call<UserInfoEntity>

}