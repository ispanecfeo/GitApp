package ru.gb.ispanecfeo.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login",])
data class UserInfoEntity(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String,
    val location: String
)