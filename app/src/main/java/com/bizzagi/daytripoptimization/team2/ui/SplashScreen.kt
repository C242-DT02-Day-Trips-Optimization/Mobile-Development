package com.bizzagi.daytripoptimization.team2.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bizzagi.daytripoptimization.team2.databinding.ActivitySplashScreenBinding
import com.bizzagi.daytripoptimization.team2.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
            finish()
        }, 3000)
    }
}