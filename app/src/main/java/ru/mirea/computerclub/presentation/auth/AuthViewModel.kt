package ru.mirea.computerclub.presentation.auth

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
import ru.mirea.computerclub.data.network.dtos.UserIdDto
import ru.mirea.computerclub.domain.AuthUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    fun signUp(email: String, password: String, name: String, birthDate: String) {
        viewModelScope.launch {
            authUseCase.signUp(email, password, name, birthDate).flowOn(Dispatchers.IO).collect { res ->
                when (res) {
                    is ApiResponse.Success<UserIdDto> -> _userId.postValue(res.data.userId ?: -1)
                    is ApiResponse.Error -> _error.postValue(res.e)
                }
            }

        }
    }


    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.signIn(email, password).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is ApiResponse.Success<UserIdDto> -> _userId.postValue(res.data.userId ?: -1)
                    is ApiResponse.Error -> _error.postValue(res.e)
                }
            }
        }
    }

}