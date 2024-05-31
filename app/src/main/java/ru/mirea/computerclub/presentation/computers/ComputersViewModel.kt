package ru.mirea.computerclub.presentation.computers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.mirea.computerclub.domain.BasketUseCase
import ru.mirea.computerclub.domain.ComputerUseCase
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class ComputersViewModel @Inject constructor(
    private val computerUseCase: ComputerUseCase,
    private val basketUseCase: BasketUseCase
): ViewModel() {

    private val _computers = MutableLiveData<PagingData<Computer>>()
    val computers: LiveData<PagingData<Computer>> = _computers

    private val _isInBasket = MutableLiveData<Boolean>()
    val isInBasket: LiveData<Boolean> = _isInBasket

    private var searchJob: Job? = null

    suspend fun getAllComputers() = computerUseCase.getAllComputers()
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)
        .collectLatest{
            _computers.postValue(it)
        }

    fun searchComputers(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000)

            computerUseCase.searchComputers(query)
                .cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
                .collect{
                    _computers.postValue(it)
                }
        }
    }

    fun addToBasket(computerId: Int) {
        viewModelScope.launch {
            basketUseCase.addComputerToBasket(computerId)
        }
    }

    fun removeFromBasket(computerId: Int) {
        viewModelScope.launch {
            basketUseCase.removeFromBasket(computerId)
        }
    }

    fun isInBasket(computerId: Int) {
        viewModelScope.launch {
            basketUseCase.isInBasket(computerId).flowOn(Dispatchers.IO).collect{
                _isInBasket.postValue(it)
            }
        }
    }

}