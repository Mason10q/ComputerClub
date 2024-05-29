package ru.mirea.computerclub.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.computerclub.data.network.ApiResponse
import ru.mirea.computerclub.domain.AuthUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _userId = MutableStateFlow<Int?>(null)
    val userId = _userId.asStateFlow()

    fun signIn(email: String, password: String, name: String, birthDate: String) {
        viewModelScope.launch {
            when (val res = authUseCase.signIn(email, password, name, birthDate)) {
                is ApiResponse.Success<*> -> res.data.flowOn(Dispatchers.IO).collect { id ->
                    _userId.update { id as Int }
                }

                is ApiResponse.Error -> _error.postValue(res.e)
            }
        }
    }


    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            when (val res = authUseCase.signUp(email, password)) {
                is ApiResponse.Success<*> -> res.data.flowOn(Dispatchers.IO).collect { id ->
                    _userId.update { id as Int }
                }

                is ApiResponse.Error -> _error.postValue(res.e)
            }
        }
    }

}