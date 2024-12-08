package com.bizzagi.daytripoptimization.team2.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bizzagi.daytripoptimization.team2.MainActivity
import com.bizzagi.daytripoptimization.team2.ViewModelFactory
import com.bizzagi.daytripoptimization.team2.customview.CustomEditText
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences
import com.bizzagi.daytripoptimization.team2.databinding.ActivityLoginBinding
import com.bizzagi.daytripoptimization.team2.ui.register.RegisterActivity
import com.bizzagi.daytripoptimization.team2.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences(this)
        if (userPreferences.getToken() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setupViews()
        setupViewModel()
        setupAction()
        checkRegisterIntent()
    }

    private fun setupViews() {
        binding.apply {
            usernameEditText.setInputType(CustomEditText.InputType.EMAIL)
            passwordEditText.setInputType(CustomEditText.InputType.PASSWORD)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[LoginViewModel::class.java]
    }

    private fun checkRegisterIntent() {
        if (intent.getBooleanExtra("REGISTER_SUCCESS", false)) {
            val email = intent.getStringExtra("EMAIL")
            binding.usernameEditText.setText(email)
            Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                val identifier = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                performLogin(identifier, password)
            }
            registerTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            forgotPasswordTextView.setOnClickListener {
                Toast.makeText(
                    this@LoginActivity,
                    "Forgot Password feature coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun performLogin(identifier: String, password: String) {
        viewModel.login(identifier, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loginButton.isEnabled = false
                    binding.linearProgressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.loginButton.isEnabled = true
                    binding.linearProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Login failed: ${result.error}", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.loginButton.isEnabled = true
                    binding.linearProgressBar.visibility = View.GONE
                    val data = result.data
                    if (data.message == "Login successful") {
                        val userPreferences = UserPreferences(this)
                        userPreferences.setToken(data.idToken)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}