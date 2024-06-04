package ru.mirea.computerclub.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.mirea.computerclub.domain.AuthUseCase
import ru.mirea.computerclub.domain.entities.Purchase
import ru.mirea.computerclub.domain.entities.User
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _purchase = MutableLiveData<PagingData<Purchase>>()
    val purchase: LiveData<PagingData<Purchase>> = _purchase

    fun getUserData() {
        viewModelScope.launch {
            authUseCase.getUserData().flowOn(Dispatchers.IO).collect {
                _user.postValue(it)
            }
        }
    }

    fun getPurchaseHistory() {
        viewModelScope.launch {
            authUseCase.getPurchaseData()
                .cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
                .collect{
                    _purchase.postValue(it)
                }
        }
    }

}