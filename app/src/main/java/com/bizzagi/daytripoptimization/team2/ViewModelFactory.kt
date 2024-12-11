package com.bizzagi.daytripoptimization.team2

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bizzagi.daytripoptimization.team2.data.repository.AuthRepository
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences
import com.bizzagi.daytripoptimization.team2.di.Injection
import com.bizzagi.daytripoptimization.team2.ui.login.LoginViewModel
import com.bizzagi.daytripoptimization.team2.ui.register.RegisterViewModel

class ViewModelFactory(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository, userPreferences) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideAuthRepository(context),
                    UserPreferences(context)
                )
            }.also { instance = it }
    }
}