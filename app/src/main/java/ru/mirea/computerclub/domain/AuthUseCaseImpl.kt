package ru.mirea.computerclub.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.ApiResponse
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.unwrapResponse
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val repository: ComputerClubRepository
): AuthUseCase {

    override suspend fun signUp(email: String, password: String, name: String, birthDate: String) = repository.signUp(
        UserDto(email, password, name, birthDate)
    ).run {
        flowOf(unwrapResponse(this))
    }

    override suspend fun signIn(email: String, password: String)= repository.signIn(
        UserDto(email, password)
    ).run {
        flowOf(unwrapResponse(this))
    }

    override suspend fun removeAccount(userId: Int) = repository.removeAccount(userId)

}