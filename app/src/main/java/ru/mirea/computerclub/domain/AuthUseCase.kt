package ru.mirea.computerclub.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mirea.computerclub.data.network.ApiResponse
import ru.mirea.computerclub.data.network.dtos.UserIdDto
import ru.mirea.computerclub.domain.entities.Purchase
import ru.mirea.computerclub.domain.entities.User

interface AuthUseCase {

    suspend fun signUp(email: String, password: String, name: String, birthDate: String): Flow<ApiResponse<UserIdDto>>

    suspend fun signIn(email: String, password: String): Flow<ApiResponse<UserIdDto>>

    suspend fun removeAccount(userId: Int)

    fun getUserData(): Flow<User>

    fun getPurchaseData(): Flow<PagingData<Purchase>>

}