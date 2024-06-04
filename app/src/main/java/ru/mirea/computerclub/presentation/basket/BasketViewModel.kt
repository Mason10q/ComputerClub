package ru.mirea.computerclub.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.mirea.computerclub.domain.BasketUseCase
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class BasketViewModel @Inject constructor(private val basketUseCase: BasketUseCase): ViewModel() {

    private val _basket = MutableLiveData<PagingData<Computer>>()
    val basket: LiveData<PagingData<Computer>> = _basket

    suspend fun getBasket() = basketUseCase.getBasket()
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)
        .collectLatest{
            _basket.postValue(it)
        }


    fun removeFromBasket(computerId: Int) {
        viewModelScope.launch {
            basketUseCase.removeFromBasket(computerId)
        }
    }

    fun buyComputers(computerIds: List<Int>) {
        viewModelScope.launch {
            basketUseCase.buyComputers(computerIds)
        }
    }

}