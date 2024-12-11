package com.bizzagi.daytripoptimization.team2.data.repository

import com.bizzagi.daytripoptimization.team2.data.api.ApiService
import com.bizzagi.daytripoptimization.team2.data.request.LoginRequest
import com.bizzagi.daytripoptimization.team2.data.request.RegisterRequest
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse
import com.bizzagi.daytripoptimization.team2.data.response.UserDetailsResponse

class AuthRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun login(identifier: String, password: String): LoginResponse {
        val request = LoginRequest(identifier, password)
        return apiService.login(request)
    }

    suspend fun register(username: String, email: String, password: String, confirmPassword: String): RegisterResponse {
        val request = RegisterRequest(username, email, password, confirmPassword)
        return apiService.register(request)
    }

    suspend fun getUserDetails(userId: String): UserDetailsResponse {
        return apiService.getUserDetails(userId)
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(apiService: ApiService): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}