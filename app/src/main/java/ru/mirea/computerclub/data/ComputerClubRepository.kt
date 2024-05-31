package ru.mirea.computerclub.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.network.dtos.InBasketDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.dtos.UserIdDto

interface ComputerClubRepository {

    suspend fun signIn(user: UserDto): Response<UserIdDto>

    suspend fun signUp(user: UserDto): Response<UserIdDto>

    suspend fun removeAccount(userId: Int)

    suspend fun getAllComputers(pageNum: Int): Response<List<ComputerDto>>

    suspend fun searchComputers(query: String, pageNum: Int): Response<List<ComputerDto>>

    suspend fun getBasket(pageNum: Int): Response<List<ComputerDto>>

    suspend fun addComputerToBasket(computerId: Int)

    suspend fun removeFromBasket(computerId: Int)

    fun isInBasket(computerId: Int): Flow<InBasketDto>
}