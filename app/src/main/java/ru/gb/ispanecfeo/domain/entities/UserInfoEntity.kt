package ru.gb.ispanecfeo.domain.entities

import com.google.gson.annotations.SerializedName

data class UserInfoEntity(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String,
    val location: String
)