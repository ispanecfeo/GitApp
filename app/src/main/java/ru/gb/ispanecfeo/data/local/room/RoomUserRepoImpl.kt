package ru.gb.ispanecfeo.data.local.room


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.gb.ispanecfeo.App
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo

class RoomUserRepoImpl(private val dao: UserDao): UserRepo.Local {

    override fun getUsers(): Single<List<UserEntity>> = dao.getUsers()
    override fun getInfoUser(login: String): Single<UserInfoEntity> = dao.getUserInfo(login)
    override fun addUsers(usersList: List<UserEntity>): Completable = dao.addUsers(usersList)
    override fun addUserInfo(userInfo: UserInfoEntity): Completable = dao.addUserInfo(userInfo)

}