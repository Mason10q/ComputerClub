package ru.mirea.computerclub.domain.mappers

import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.domain.entities.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserDto, User> {

    override fun map(item: UserDto): User =
        User(item.email ?: "", item.password ?: "", item.name, item.birthDate)
}