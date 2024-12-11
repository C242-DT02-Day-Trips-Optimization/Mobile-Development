package com.bizzagi.daytripoptimization.team2.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setToken(token: String) {
        prefs.edit { putString("token", token) }
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun setEmail(email: String) {
        prefs.edit { putString("email", email) }
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun setUsername(username: String) {
        prefs.edit { putString("username", username) }
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}