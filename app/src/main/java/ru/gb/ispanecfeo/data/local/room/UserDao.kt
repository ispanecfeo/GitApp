package ru.gb.ispanecfeo.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity ORDER BY id")
    fun getUsers() : Single<List<UserEntity>>

    @Query("SELECT * FROM UserInfoEntity WHERE login = :login LIMIT 1")
    fun getUserInfo(login: String): Single<UserInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(usersList: List<UserEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserInfo(userInfo: UserInfoEntity): Completable

}