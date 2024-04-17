package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.UserLogin
import com.example.reservationproject.model.UserLoginResponse
import com.example.reservationproject.repo.AuthService
import com.example.reservationproject.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _passwordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible
    private var loginListener: LoginListener? = null
    val retrofit = RetrofitClient
    private val LoginService = retrofit.getClient().create(AuthService::class.java)
    val errorMessageLiveData = MutableLiveData<String>()

    companion object {
        var token: String? = null
    }

    interface LoginListener {
        fun onLoginSuccess(token: String)
        fun onLoginFailure(error: String)
    }
    fun setLoginListener(listener: LoginListener) {
        loginListener = listener

    }

    init {
        _passwordVisible.value = false
    }

    fun Login(username: String, password: String) {
        val user = UserLogin(username, password)

        LoginService.auth(user).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                val data = response.body()
                if (data != null) {
                    token = data.token
                }
                    Log.e("token", token.toString())
                    loginListener?.onLoginSuccess(token!!)
                    RetrofitClient.setAuthToken(token!!)
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }
}