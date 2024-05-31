package ru.mirea.computerclub.domain.mappers

import ru.mirea.computerclub.BuildConfig
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class ComputerMapper @Inject constructor(): Mapper<ComputerDto, Computer> {

    override fun map(item: ComputerDto): Computer = Computer(
        item.computerId ?: -1,
        item.computerName ?: "",
        item.computerDescription ?: "",
        item.computerPrice ?: 0,
        ("${BuildConfig.ENDPOINT_URL}/${item.computerPhotoUrl}")
    )
}