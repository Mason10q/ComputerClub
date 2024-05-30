package ru.mirea.computerclub.data.network.dtos

import com.google.gson.annotations.SerializedName

data class UserIdDto(
    @SerializedName("id")
    val userId: Int?
)