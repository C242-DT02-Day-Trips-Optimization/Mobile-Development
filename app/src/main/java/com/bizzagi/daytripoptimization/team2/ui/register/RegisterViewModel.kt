package com.bizzagi.daytripoptimization.team2.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bizzagi.daytripoptimization.team2.Result
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository
import com.bizzagi.daytripoptimization.team2.data.response.RegisterResponse

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    fun register(username: String, email: String, password: String): LiveData<Result<RegisterResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val response = repository.register(username, email, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Registration failed"))
            }
        }
    }
}