package ru.mirea.computerclub.domain.mappers

import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.network.dtos.PurchaseDto
import ru.mirea.computerclub.domain.entities.Computer
import ru.mirea.computerclub.domain.entities.Purchase
import javax.inject.Inject

class PurchaseMapper @Inject constructor(private val computerMapper: Mapper<ComputerDto, Computer>) :
    Mapper<PurchaseDto, Purchase> {

    override fun map(item: PurchaseDto): Purchase =
        Purchase(item.date ?: "none", item.computers?.map(computerMapper::map) ?: emptyList())
}