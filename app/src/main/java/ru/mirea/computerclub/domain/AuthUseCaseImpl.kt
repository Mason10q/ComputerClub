package ru.mirea.computerclub.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.dtos.PurchaseDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.unwrapResponse
import ru.mirea.computerclub.data.paging.PurchasePagingSource
import ru.mirea.computerclub.domain.entities.Purchase
import ru.mirea.computerclub.domain.entities.User
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val repository: ComputerClubRepository,
    private val profileMapper: Mapper<UserDto, User>,
    private val purchaseMapper: Mapper<PurchaseDto, Purchase>
): AuthUseCase {

    private val config = PagingConfig(
        pageSize = PurchasePagingSource.networkPageSize,
        initialLoadSize = PurchasePagingSource.initialLoad,
        prefetchDistance = PurchasePagingSource.prefetchDistance,
        enablePlaceholders = false
    )

    override suspend fun signUp(email: String, password: String, name: String, birthDate: String) =
        repository.signUp(
            UserDto(email, password, name, birthDate)
        ).run {
            flowOf(unwrapResponse(this))
        }

    override suspend fun signIn(email: String, password: String) = repository.signIn(
        UserDto(email, password)
    ).run {
        flowOf(unwrapResponse(this))
    }

    override suspend fun removeAccount(userId: Int) = repository.removeAccount(userId)

    override fun getUserData(): Flow<User> = repository.getProfileData()
        .map(profileMapper::map)

    override fun getPurchaseData(): Flow<PagingData<Purchase>> = Pager(
        config = this.config,
        initialKey = 1,
        pagingSourceFactory = { PurchasePagingSource(repository, purchaseMapper) }
    ).flow


}