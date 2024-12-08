package com.bizzagi.daytripoptimization.team2.di

import android.content.Context
import com.bizzagi.daytripoptimization.team2.data.api.ApiConfig
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService(context)
        return AuthRepository.getInstance(apiService)
    }
}