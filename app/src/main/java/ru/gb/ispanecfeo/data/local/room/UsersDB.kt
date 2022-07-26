package ru.gb.ispanecfeo.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gb.ispanecfeo.domain.entities.UserEntity
import ru.gb.ispanecfeo.domain.entities.UserInfoEntity

@Database(entities = [UserEntity::class, UserInfoEntity::class], version = 1, exportSchema = false)
abstract class UsersDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var dbInstance: UsersDB? = null
        private const val dbName: String = "github_users.db"

        fun getDB(context: Context): UserDao? {

            synchronized(UsersDB::class.java) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(
                        context,
                        UsersDB::class.java,
                        dbName
                    )
                    .build()
                }
            }
            return dbInstance?.userDao()
        }

    }

}