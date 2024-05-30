package ru.mirea.computerclub.data

import retrofit2.Response
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.dtos.UserIdDto

interface ComputerClubRepository {

    suspend fun signIn(user: UserDto): Response<UserIdDto>

    suspend fun signUp(user: UserDto): Response<UserIdDto>

    suspend fun removeAccount(userId: Int)

    suspend fun getAllComputers(pageNum: Int): Response<List<ComputerDto>>

    suspend fun searchComputers(query: String, pageNum: Int): Response<List<ComputerDto>>
}