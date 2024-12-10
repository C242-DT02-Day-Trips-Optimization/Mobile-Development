package com.bizzagi.daytripoptimization.team2.data.api

import android.content.Context
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val AUTH_BASE_URL = "https://auth-api-996559796971.asia-southeast2.run.app/"
    private const val CLUSTERING_BASE_URL = "https://clustering-api-996559796971.asia-southeast2.run.app/"

    fun getAuthApiService(context: Context): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val userPreferences = UserPreferences(context)
        val authInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            userPreferences.getToken()?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun getClusteringApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(CLUSTERING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}