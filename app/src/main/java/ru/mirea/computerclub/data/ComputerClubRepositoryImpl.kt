package ru.mirea.computerclub.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.mirea.computerclub.data.network.ComputerClubApi
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.network.dtos.InBasketDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import javax.inject.Inject

class ComputerClubRepositoryImpl @Inject constructor(
    private val api: ComputerClubApi,
    private val userId: Int
): ComputerClubRepository {

    override suspend fun signIn(user: UserDto) = api.signIn(user)

    override suspend fun signUp(user: UserDto) = api.signUp(user)

    override suspend fun removeAccount(userId: Int) = api.removeProfile(userId)

    override suspend fun getAllComputers(pageNum: Int) = api.getAllComputers(pageNum)

    override suspend fun searchComputers(query: String, pageNum: Int)= api.searchComputers(query, pageNum)

    override suspend fun getBasket(pageNum: Int): Response<List<ComputerDto>> = api.getBasket(userId, pageNum)

    override suspend fun addComputerToBasket(computerId: Int) = api.addComputerToBasket(userId, computerId)

    override suspend fun removeFromBasket(computerId: Int) = api.removeFromBasket(userId, computerId)
    override fun isInBasket(computerId: Int): Flow<InBasketDto> = api.isInBasket(userId, computerId)
}