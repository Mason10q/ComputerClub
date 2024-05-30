package ru.mirea.computerclub.data

import ru.mirea.computerclub.data.network.ComputerClubApi
import ru.mirea.computerclub.data.network.dtos.UserDto
import javax.inject.Inject

class ComputerClubRepositoryImpl @Inject constructor(private val api: ComputerClubApi): ComputerClubRepository {

    override suspend fun signIn(user: UserDto) = api.signIn(user)

    override suspend fun signUp(user: UserDto) = api.signUp(user)

    override suspend fun removeAccount(userId: Int) = api.removeProfile(userId)

    override suspend fun getAllComputers(pageNum: Int) = api.getAllComputers(pageNum)

    override suspend fun searchComputers(query: String, pageNum: Int)= api.searchComputers(query, pageNum)
}