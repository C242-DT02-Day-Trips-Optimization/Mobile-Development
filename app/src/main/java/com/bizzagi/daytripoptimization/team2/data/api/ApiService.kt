package com.bizzagi.daytripoptimization.team2.data.api

import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.request.LoginRequest
import com.bizzagi.daytripoptimization.team2.data.request.RegisterRequest
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("user/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("recommend/")
    suspend fun getClusteringRecommendation(
        @Body request: ClusteringRequest
    ): ClusteringResponse
}