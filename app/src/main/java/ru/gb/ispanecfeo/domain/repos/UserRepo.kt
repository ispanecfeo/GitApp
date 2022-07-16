package ru.gb.ispanecfeo.domain.repos

import retrofit2.Callback
import retrofit2.http.Path
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

interface UserRepo {

    fun getUsers(callback: Callback<List<UserEntity>>)
    fun getInfoUser(login: String, callback: Callback<UserInfoEntity>)
}