package ru.mirea.computerclub.data.network.dtos

import com.google.gson.annotations.SerializedName

data class ComputerDto(
    @SerializedName("id")
    val computerId: Int?,
    @SerializedName("computer_name")
    val computerName: String?,
    @SerializedName("price")
    val computerPrice: Int?,
    @SerializedName("description")
    val computerDescription: String?,
    @SerializedName("photo_url")
    val computerPhotoUrl: String?
)