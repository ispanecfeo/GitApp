package ru.gb.ispanecfeo.domain.repos

import retrofit2.Callback
import ru.gb.ispanecfeo.domain.entities.UserEntity

interface UserRepo {

    fun getUsers(callback: Callback<List<UserEntity>>)
}