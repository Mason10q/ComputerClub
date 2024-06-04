package ru.mirea.computerclub.data.network.dtos

import com.google.gson.annotations.SerializedName

data class PurchaseDto(
    @SerializedName("date")
    val date: String?,
    @SerializedName("computers")
    val computers: List<ComputerDto>?
)