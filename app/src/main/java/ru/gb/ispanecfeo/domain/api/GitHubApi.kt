package ru.gb.ispanecfeo.domain.api

import retrofit2.Call
import retrofit2.http.GET
import ru.gb.ispanecfeo.domain.entities.UserEntity

interface GitHubApi {

    @GET("users")
    fun getGitHubUsers() : Call<List<UserEntity>>

}