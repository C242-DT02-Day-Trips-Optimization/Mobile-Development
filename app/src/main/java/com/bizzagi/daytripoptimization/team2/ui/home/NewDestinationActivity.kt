package com.bizzagi.daytripoptimization.team2.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDestinationBinding
import com.bizzagi.daytripoptimization.team2.databinding.ItemNewDestinationBinding

class NewDestinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewDestinationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbarDestination
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.fabAddInput.setOnClickListener {
            addNewInputRow()
        }

        val province = intent.getStringExtra("PROVINCE") ?: ""
        Log.d("NewDestinationActivity", "Received province: $province") // Log untuk debugging

        binding.btnDateTime.setOnClickListener {
            val destinations = mutableListOf<String>()
            for (i in 0 until binding.layoutInputContainer.childCount) {
                val rowBinding = ItemNewDestinationBinding.bind(binding.layoutInputContainer.getChildAt(i))
                val destinationName = rowBinding.inputDestination.editText?.text.toString()
                if (destinationName.isNotEmpty()) {
                    destinations.add(destinationName)
                }
            }

            Log.d("NewDestinationActivity", "Destinations: $destinations") // Log untuk debugging
            Log.d("NewDestinationActivity", "Sending province: $province") // Log untuk debugging

            val intent = Intent(this, NewDayTimeActivity::class.java).apply {
                putExtra("PROVINCE", province)
                putStringArrayListExtra("DESTINATIONS", ArrayList(destinations))
            }
            startActivity(intent)
        }
    }

    private fun addNewInputRow() {
        val rowBinding = ItemNewDestinationBinding.inflate(LayoutInflater.from(this))
        binding.layoutInputContainer.addView(rowBinding.root)

        rowBinding.btnRemoveDestination.setOnClickListener {
            binding.layoutInputContainer.removeView(rowBinding.root)
        }
    }
}