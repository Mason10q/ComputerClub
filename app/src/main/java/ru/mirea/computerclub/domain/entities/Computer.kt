package ru.mirea.computerclub.domain.entities

import java.io.Serializable

data class Computer(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val photoUrl: String
): Serializable