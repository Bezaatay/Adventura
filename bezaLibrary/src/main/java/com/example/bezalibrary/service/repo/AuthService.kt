package com.example.bezalibrary.service.repo

import com.example.bezalibrary.service.model.NewUser
import com.example.bezalibrary.service.model.RegisterResponse
import com.example.bezalibrary.service.model.UserLogin
import com.example.bezalibrary.service.model.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("SignIn/Login")
    fun auth(@Body user: UserLogin): Call<UserLoginResponse>
    @POST("Registers/CreateUser")
    fun createUser(@Body user: NewUser): Call<RegisterResponse>
}