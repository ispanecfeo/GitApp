package ru.gb.ispanecfeo.data.local.room

import io.reactivex.rxjava3.core.Single
import ru.gb.ispanecfeo.data.api.GitHubApi
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity
import ru.gb.ispanecfeo.domain.repos.UserRepo


class RetrofitUserRepoImpl(private val api: GitHubApi) : UserRepo.Remote {

    override fun getUsers(): Single<List<UserEntity>> = api.getGitHubUsers()
    override fun getInfoUser(login: String): Single<UserInfoEntity> = api.getGitHubUserInfo(login)

}