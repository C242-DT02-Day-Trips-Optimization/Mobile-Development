package com.bizzagi.daytripoptimization.team2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.Result
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences

class LoginViewModel(private val repository: AuthRepository, private val userPreferences: UserPreferences) : ViewModel() {
    fun login(identifier: String, password: String): LiveData<Result<LoginResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val response = repository.login(identifier, password)
                if (response.message == "Login successful") {
                    // Simpan token
                    userPreferences.setToken(response.idToken)

                    // Ambil userId dari response
                    val userId = response.userId // Pastikan response memiliki userId

                    // Ambil detail pengguna
                    val userDetailsResponse = repository.getUserDetails(userId)
                    userPreferences.setEmail(userDetailsResponse.email) // Simpan email
                    userPreferences.setUsername(userDetailsResponse.username) // Simpan username

                    emit(Result.Success(response))
                } else {
                    emit(Result.Success(response))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Login failed"))
            }
        }
    }
}