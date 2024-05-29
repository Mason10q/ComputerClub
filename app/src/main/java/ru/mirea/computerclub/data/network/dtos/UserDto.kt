package ru.mirea.computerclub.data.network

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("birthDate")
    val birthDate: String? = null
)