package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.manager.AppPref

class LoginViewModel : ViewModel() {
    private val functions = Functions()

    private var _passwordVisible = MutableLiveData<Boolean>()
    var passwordVisible: LiveData<Boolean> = _passwordVisible

    private var _isSuccessLogin = MutableLiveData<Boolean>()
    var isSuccessLogin: LiveData<Boolean> = _isSuccessLogin

    private var _token = MutableLiveData<String>()
    var token: LiveData<String> = _token

    init {
        _passwordVisible.value = false

    }

    fun loginSuccess(username: String, password: String){
        functions.login(username,password).observeForever{
            _isSuccessLogin.value = true
            _token.value = it
        }
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }
}