package com.bizzagi.daytripoptimization.team2.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences
import com.bizzagi.daytripoptimization.team2.databinding.FragmentProfileBinding
import com.bizzagi.daytripoptimization.team2.ui.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())

        // Set email and username
        setUserInfo()

        // Implement logout functionality
        binding.btnLogout.setOnClickListener {
            logout()
        }

        return binding.root
    }

    private fun setUserInfo() {
        // Retrieve user information from UserPreferences
        val email = userPreferences.getEmail()
        val username = userPreferences.getUsername()

        // Set the email and username in the UI
        binding.loggedEmail.text = email ?: "Not available"
        binding.loggedUsername.text = username ?: "Not available"
    }

    private fun logout() {
        // Clear user data from UserPreferences
        userPreferences.clear()

        // Navigate to LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}