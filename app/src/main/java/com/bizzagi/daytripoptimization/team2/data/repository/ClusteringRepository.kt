package com.bizzagi.daytripoptimization.team2.data.repository

import com.bizzagi.daytripoptimization.team2.data.api.ApiService
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.Result

class ClusteringRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun getClusteringRecommendation(request: ClusteringRequest): Result<ClusteringResponse> {
        return try {
            val response = apiService.getClusteringRecommendation(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    companion object {
        @Volatile
        private var instance: ClusteringRepository? = null

        fun getInstance(apiService: ApiService): ClusteringRepository =
            instance ?: synchronized(this) {
                instance ?: ClusteringRepository(apiService)
            }.also { instance = it }
    }
}