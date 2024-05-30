package ru.mirea.computerclub.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mirea.computerclub.domain.entities.Computer

interface ComputerUseCase {

    fun searchComputers(query: String): Flow<PagingData<Computer>>

    fun getAllComputers(): Flow<PagingData<Computer>>

}