package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions

class LoginViewModel : ViewModel() {
    private val functions = Functions()

    private var _passwordVisible = MutableLiveData<Boolean>()
    var passwordVisible: LiveData<Boolean> = _passwordVisible

    private var _isSuccessLogin = MutableLiveData<Boolean>()
    var isSuccessLogin: LiveData<Boolean> = _isSuccessLogin

    init {
        _passwordVisible.value = false

    }

    fun loginSuccess(username: String, password: String){
       val isToken =  functions.login(username,password)
        if(isToken!=null){
            _isSuccessLogin.value = true
        }
        else{
            _isSuccessLogin.value = false
        }
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }
}