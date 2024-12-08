package com.bizzagi.daytripoptimization.team2.data.api

import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("user/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
}

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirm_password: String
)