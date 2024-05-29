package ru.mirea.computerclub.data

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val e: Throwable): ApiResponse<Nothing>()

}