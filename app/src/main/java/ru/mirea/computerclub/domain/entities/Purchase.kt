package ru.mirea.computerclub.domain.entities

data class Purchase(
    val date: String,
    val computers: List<Computer>
)