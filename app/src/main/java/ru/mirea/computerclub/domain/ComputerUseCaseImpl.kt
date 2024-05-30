package ru.mirea.computerclub.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.paging.ComputerPagingSource
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class ComputerUseCaseImpl @Inject constructor(
    private val repository: ComputerClubRepository,
    private val computerMapper: Mapper<ComputerDto, Computer>
) : ComputerUseCase {

    private val config = PagingConfig(
        pageSize = ComputerPagingSource.networkPageSize,
        initialLoadSize = ComputerPagingSource.initialLoad,
        prefetchDistance = ComputerPagingSource.prefetchDistance,
        enablePlaceholders = false
    )

    override fun searchComputers(query: String): Flow<PagingData<Computer>> = Pager(
        config = this.config,
        initialKey = 1,
        pagingSourceFactory = { ComputerPagingSource(repository, computerMapper, query) }
    ).flow

    override fun getAllComputers(): Flow<PagingData<Computer>> = Pager(
        config = this.config,
        initialKey = 1,
        pagingSourceFactory = { ComputerPagingSource(repository, computerMapper, null) }
    ).flow

}