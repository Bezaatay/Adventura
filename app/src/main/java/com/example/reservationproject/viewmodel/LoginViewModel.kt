package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bezalibrary.service.Functions
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val functions = Functions()

    private var _passwordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible

    private var _isSuccessLogin = MutableLiveData<Boolean>()
    val isSuccessLogin: LiveData<Boolean> = _isSuccessLogin

    private var _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    init {
        _passwordVisible.value = false
    }

    fun loginSuccess(username: String, password: String) {
        viewModelScope.launch {
            try {
                val tokenResult = functions.login(username, password)
                _token.postValue(tokenResult)
                _isSuccessLogin.postValue(true)
            } catch (e: Exception) {
                _isSuccessLogin.postValue(false)
            }
        }
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }
}
