package com.bizzagi.daytripoptimization.team2.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bizzagi.daytripoptimization.team2.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // !TODO: Set email dan password user yang masuk ke aplikasi
        // binding.loggedEmail.text =
        // binding.loggedUsername.text =

        binding.btnLogout.setOnClickListener {
            // !TODO: implementasi action ketika button logout di klik
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}