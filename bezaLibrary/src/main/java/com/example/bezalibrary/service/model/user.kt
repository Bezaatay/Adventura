package com.example.bezalibrary.service.model


data class NewUser(
    val email: String,
    val username: String,
    val password: String,
    val name: String,
    val surname: String
)

data class UserLogin(
    val username: String,
    val password: String
)

data class UserLoginResponse(
    val token: String,
    val expireDate: String
)

data class RegisterResponse(
    val message: String,
)