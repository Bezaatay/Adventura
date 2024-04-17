package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.NewUser
import com.example.reservationproject.model.RegisterResponse
import com.example.reservationproject.repo.AuthService
import com.example.reservationproject.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val retrofit = RetrofitClient
    val userApi = retrofit.getClient().create(AuthService::class.java)


    private val _passwordVisible = MutableLiveData<Boolean>()
    private val _CpasswordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible
    val CpasswordVisible: LiveData<Boolean> = _CpasswordVisible

    init {
        // Şifre başlangıçta gizli
        _passwordVisible.value = false
        _CpasswordVisible.value = false
    }

    fun createUser(newUser: NewUser) {
        userApi.createUser(newUser).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val data = response.body()
                if (data?.message != null) {
                    if(data.message == "Kullanıcı Oluşturuldu"){
                        Log.e("kullnıcı olusturuldu","ok")
                    }
                }
                else{
                    Log.e("HATA","Kullanıcı Oluşturulamadı")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            }
        })
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value!!
    }

    fun toggleCPasswordVisibility() {
        _CpasswordVisible.value = !_CpasswordVisible.value!!
    }
}