package com.bizzagi.daytripoptimization.team2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository
import com.bizzagi.daytripoptimization.team2.data.response.LoginResponse
import com.bizzagi.daytripoptimization.team2.Result

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    fun login(identifier: String, password: String): LiveData<Result<LoginResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val response = repository.login(identifier, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Login failed"))
            }
        }
    }
}