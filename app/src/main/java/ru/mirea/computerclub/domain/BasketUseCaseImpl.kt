package ru.mirea.computerclub.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.paging.BasketPagingSource
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class BasketUseCaseImpl @Inject constructor(
    private val repository: ComputerClubRepository,
    private val basketMapper: Mapper<ComputerDto, Computer>
): BasketUseCase {

    private val config = PagingConfig(
        pageSize = BasketPagingSource.networkPageSize,
        initialLoadSize = BasketPagingSource.initialLoad,
        prefetchDistance = BasketPagingSource.prefetchDistance,
        enablePlaceholders = false
    )

    override fun getBasket(): Flow<PagingData<Computer>> = Pager(
        config = this.config,
        initialKey = 1,
        pagingSourceFactory = { BasketPagingSource(repository, basketMapper) }
    ).flow

    override suspend fun addComputerToBasket(computerId: Int) =
        repository.addComputerToBasket(computerId)

    override suspend fun removeFromBasket(computerId: Int) =
        repository.removeFromBasket(computerId)

    override fun isInBasket(computerId: Int): Flow<Boolean> = repository.isInBasket(computerId)
        .map { it.isInBasket == 1 }
}