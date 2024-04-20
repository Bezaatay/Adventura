package com.example.reservationproject.model
data class UserData(
    val _id: String,
    val username: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val email: String,
    val city: String,
    val location: String,
    val latitude: String,
    val longitude: String,
    val isVerrified: Boolean,
)

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

data class ForgotPassword(
    val message: String,
)

data class ResetPassword(
    val password: String,
    val confirmPassword: String,
)