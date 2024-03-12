package com.example.afinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _passwordVisible = MutableLiveData<Boolean>()
    private val _CpasswordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible
    val CpasswordVisible: LiveData<Boolean> = _CpasswordVisible
    init {
        // Şifre başlangıçta gizli
        _passwordVisible.value = false
        _CpasswordVisible.value = false
    }

    fun togglePasswordVisibility() {
        // Şifre görünürlüğünü tersine çevir
        _passwordVisible.value = !_passwordVisible.value!!
    }
    fun toggleCPasswordVisibility() {
        // Şifre görünürlüğünü tersine çevir
        _CpasswordVisible.value = !_CpasswordVisible.value!!
    }
}