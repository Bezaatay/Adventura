package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.service.RetrofitClient

class LoginViewModel : ViewModel() {

    private val _passwordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible


    interface LoginListener {
        fun onLoginSuccess(token: String)
        fun onLoginFailure(error: String)
    }

    init {
        _passwordVisible.value = false
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }
}