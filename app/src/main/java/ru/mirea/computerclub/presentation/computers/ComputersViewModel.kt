package ru.mirea.computerclub.presentation.computers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import ru.mirea.computerclub.domain.ComputerUseCase
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class ComputersViewModel @Inject constructor(private val computerUseCase: ComputerUseCase): ViewModel() {

    private val _computers = MutableLiveData<PagingData<Computer>>()
    val computers: LiveData<PagingData<Computer>> = _computers

    suspend fun getAllComputers() = computerUseCase.getAllComputers()
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)
        .collectLatest{
            _computers.postValue(it)
        }

    @OptIn(FlowPreview::class)
    suspend fun searchComputers(query: String) = computerUseCase.searchComputers(query)
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)
        .debounce(1000)
        .collect{
            _computers.postValue(it)
        }



}