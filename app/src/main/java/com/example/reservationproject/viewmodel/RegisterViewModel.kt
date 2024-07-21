package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.NewUser
import com.example.reservationproject.manager.AppPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    private val _passwordVisible = MutableLiveData<Boolean>()
    private val _CpasswordVisible = MutableLiveData<Boolean>()
    private val _registrationSuccess = MutableLiveData<Boolean>()
    private val _registrationError = MutableLiveData<String>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible
    val CpasswordVisible: LiveData<Boolean> = _CpasswordVisible
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess
    val registrationError: LiveData<String> = _registrationError

    private val functions = Functions()
    private lateinit var appPref: AppPref

    init {
        _passwordVisible.value = false
        _CpasswordVisible.value = false
    }

    fun initializeAppPref(appPref: AppPref) {
        this.appPref = appPref
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }

    fun toggleCPasswordVisibility() {
        _CpasswordVisible.value = !_CpasswordVisible.value!!
    }

    fun registerUser(
        email: String, username: String, password: String, name: String, surname: String,
        confirmPassword: String
    ) {
        when {
            password.isBlank() -> _registrationError.value = "Şifre boş bırakılamaz!"
            confirmPassword.isBlank() -> _registrationError.value = "Şifre tekrarı boş bırakılamaz!"
            confirmPassword != password -> _registrationError.value = "Şifreler Uyuşmuyor!"
            else -> {
                viewModelScope.launch {
                    try {
                        val user = NewUser(email, username, password, name, surname)
                        withContext(Dispatchers.IO) {
                            functions.createUser(user)
                        }
                        appPref.saveNameAndSurname(name, surname)
                        appPref.saveMail(email)
                        _registrationSuccess.value = true
                    } catch (e: Exception) {
                        _registrationError.value = "Kullanıcı oluşturulurken bir hata oluştu."
                    }
                }
            }
        }
    }
}
