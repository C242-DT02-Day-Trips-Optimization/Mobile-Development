package com.bizzagi.daytripoptimization.team2.data.repository

import com.bizzagi.daytripoptimization.team2.data.api.ApiService
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse

class ClusteringRepository private constructor(
    private val apiService: ApiService
) {
    // Fungsi untuk mendapatkan rekomendasi klustering
    suspend fun getClusteringRecommendation(request: ClusteringRequest): ClusteringResponse {
        return apiService.getClusteringRecommendation(request)
    }

    companion object {
        @Volatile
        private var instance: ClusteringRepository? = null

        // Fungsi untuk mendapatkan instance singleton dari ClusteringRepository
        fun getInstance(apiService: ApiService): ClusteringRepository =
            instance ?: synchronized(this) {
                instance ?: ClusteringRepository(apiService)
            }.also { instance = it }
    }
}