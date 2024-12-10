package com.bizzagi.daytripoptimization.team2.data.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirm_password: String
)