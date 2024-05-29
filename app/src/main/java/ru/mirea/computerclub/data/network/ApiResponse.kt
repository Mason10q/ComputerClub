package ru.mirea.computerclub.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val e: Throwable): ApiResponse<T>()
}


fun <T> unwrapResponse(response: Response<T>): ApiResponse<T> {
    val body = response.body()
    val errorBody = response.errorBody()

    return when {
        body != null -> {
            ApiResponse.Success(body)
        }
        errorBody != null -> {
            ApiResponse.Error(Exception(errorBody.string()))
        }
        else -> {
            ApiResponse.Error(Exception("Unknown error: ${response.raw().message}"))
        }
    }
}