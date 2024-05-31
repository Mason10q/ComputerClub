package ru.mirea.computerclub.data.network.dtos

import com.google.gson.annotations.SerializedName

data class InBasketDto(
    @SerializedName("is_in_basket")
    val isInBasket: Int?
)