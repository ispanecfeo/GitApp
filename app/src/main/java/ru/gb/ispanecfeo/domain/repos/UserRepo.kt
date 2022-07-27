package ru.gb.ispanecfeo.domain.repos

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Callback
import retrofit2.http.Path
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

interface UserRepo {

    interface Remote {
        fun getUsers() : Single<List<UserEntity>>
        fun getInfoUser(login: String): Single<UserInfoEntity>
    }

    interface Local{
        fun getUsers() : Single<List<UserEntity>>?
        fun getInfoUser(login: String): Single<UserInfoEntity>?

        fun addUsers(usersList: List<UserEntity>) : Completable?
        fun addUserInfo(userInfo: UserInfoEntity): Completable?
    }

}