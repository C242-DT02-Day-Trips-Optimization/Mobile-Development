package com.bizzagi.daytripoptimization.team2.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bizzagi.daytripoptimization.team2.ViewModelFactory
import com.bizzagi.daytripoptimization.team2.customview.CustomEditText
import com.bizzagi.daytripoptimization.team2.databinding.ActivityRegisterBinding
import com.bizzagi.daytripoptimization.team2.ui.login.LoginActivity
import com.bizzagi.daytripoptimization.team2.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModel()
        setupAction()
    }

    private fun setupViews() {
        binding.apply {
            emailEditText.setInputType(CustomEditText.InputType.EMAIL)
            usernameEditText.setInputType(CustomEditText.InputType.USERNAME)
            passwordEditText.setInputType(CustomEditText.InputType.PASSWORD)
            confirmPasswordEditText.setInputType(CustomEditText.InputType.CONFIRM_PASSWORD)

            // Setup password confirmation validation
            passwordEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    confirmPasswordEditText.setPasswordToCompare(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            registerButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                performRegister(username, email, password)
            }

            loginTextView.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun performRegister(username: String, email: String, password: String) {
        viewModel.register(username, email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.registerButton.isEnabled = false  // Disable button saat loading
                    binding.linearProgressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.registerButton.isEnabled = true   // Enable button kembali
                    binding.linearProgressBar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.registerButton.isEnabled = true   // Enable button kembali
                    binding.linearProgressBar.visibility = View.GONE
                    if (result.data.error == true) {
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage(result.data.message)
                            setPositiveButton("Login") { _, _ ->
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                                    putExtra("REGISTER_SUCCESS", true)
                                    putExtra("EMAIL", email)
                                }
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}