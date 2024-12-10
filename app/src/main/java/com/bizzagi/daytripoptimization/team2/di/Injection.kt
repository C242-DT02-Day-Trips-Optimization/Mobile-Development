package com.bizzagi.daytripoptimization.team2.di

import android.content.Context
import com.bizzagi.daytripoptimization.team2.data.api.ApiConfig
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository
import com.bizzagi.daytripoptimization.team2.data.repository.ClusteringRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getAuthApiService(context) // Memanggil metode yang benar
        return AuthRepository.getInstance(apiService)
    }

    fun provideClusteringRepository(): ClusteringRepository {
        val apiService = ApiConfig.getClusteringApiService() // Memanggil metode untuk klustering
        return ClusteringRepository.getInstance(apiService)
    }
}