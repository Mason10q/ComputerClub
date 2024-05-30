package ru.mirea.computerclub.core

interface Mapper<T, DATA> {

    fun map(item: T): DATA

    fun map(items: List<T>): List<DATA> = items.map(::map)

}