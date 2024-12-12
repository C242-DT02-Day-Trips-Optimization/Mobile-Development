package com.bizzagi.daytripoptimization.team2.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bizzagi.daytripoptimization.team2.data.pref.UserPreferences
import com.bizzagi.daytripoptimization.team2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())

        binding.labelWelcome.text = "Welcome, ${userPreferences.getUsername()}"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewTrip.setOnClickListener {
            val province = binding.inputTrip.editText?.text.toString()
            if (province.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a province", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("NewTripSheetFragment", "Sending province: $province") // Log untuk debugging
            val intent = Intent(requireContext(), NewDestinationActivity::class.java).apply {
                putExtra("PROVINCE", province)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}