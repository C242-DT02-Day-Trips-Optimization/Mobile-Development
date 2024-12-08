package com.bizzagi.daytripoptimization.team2.data.response

data class LoginResponse(
    val expiresIn: String,
    val idToken: String,
    val message: String,
    val refreshToken: String,
    val userId: String
)