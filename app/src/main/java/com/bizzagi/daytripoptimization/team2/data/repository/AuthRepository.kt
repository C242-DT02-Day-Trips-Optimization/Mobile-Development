package com.bizzagi.daytripoptimization.team2.data.repository

import com.bizzagi.daytripoptimization.team2.data.api.ApiService
import com.bizzagi.daytripoptimization.team2.data.api.LoginRequest
import com.bizzagi.daytripoptimization.team2.data.api.RegisterRequest
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse

class AuthRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(email, password))
    }

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return apiService.register(
            RegisterRequest(
                username = username,
                email = email,
                password = password,
                confirm_password = password
            )
        )
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}