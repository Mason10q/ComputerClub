package ru.mirea.computerclub.domain.entities

data class User(
    val email: String,
    val password: String,
    val name: String? = null,
    val birthDate: String? = null
)