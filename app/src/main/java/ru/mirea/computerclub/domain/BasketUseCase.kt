package ru.mirea.computerclub.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mirea.computerclub.domain.entities.Computer

interface BasketUseCase {

    fun getBasket(): Flow<PagingData<Computer>>

    suspend fun addComputerToBasket(computerId: Int)

    suspend fun removeFromBasket(computerId: Int)

    fun isInBasket(computerId: Int): Flow<Boolean>

    suspend fun buyComputers(computerIds: List<Int>)

}