package com.bizzagi.daytripoptimization.team2.data.api

import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.request.LoginRequest
import com.bizzagi.daytripoptimization.team2.data.request.RegisterRequest
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.data.response.UserDetailsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("user/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("user/get_user/{userId}")
    suspend fun getUserDetails(
    @Path("userId") userId: String
    ): UserDetailsResponse

    @POST("cluster/")
    suspend fun getClusteringRecommendation(
        @Body request: ClusteringRequest
    ): ClusteringResponse
}