package com.example.reservationproject.repo

import com.example.reservationproject.model.NewUser
import com.example.reservationproject.model.RegisterResponse
import com.example.reservationproject.model.UserLogin
import com.example.reservationproject.model.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("api/SignIn/Login")
    fun auth(@Body user: UserLogin): Call<UserLoginResponse>
    @POST("api/Registers/CreateUser")
    fun createUser(@Body user: NewUser): Call<RegisterResponse>
}