package com.example.bezalibrary.service.repo

import com.example.bezalibrary.service.model.NewUser
import com.example.bezalibrary.service.model.RegisterResponse
import com.example.bezalibrary.service.model.UserLogin
import com.example.bezalibrary.service.model.UserLoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("SignIn/Login")
    suspend fun auth(@Body user: UserLogin): Response<UserLoginResponse>

    @POST("Registers/CreateUser")
    suspend fun createUser(@Body user: NewUser): Response<RegisterResponse>
}