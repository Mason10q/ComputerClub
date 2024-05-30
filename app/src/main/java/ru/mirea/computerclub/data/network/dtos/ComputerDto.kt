package ru.mirea.computerclub.data.network.dtos

import com.google.gson.annotations.SerializedName

data class ComputerDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("computer_name")
    val name: String?,
    @SerializedName("photo_url")
    val photoUrl: String?
)