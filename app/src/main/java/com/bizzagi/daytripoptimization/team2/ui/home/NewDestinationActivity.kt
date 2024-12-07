package com.bizzagi.daytripoptimization.team2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDestinationBinding
import com.bizzagi.daytripoptimization.team2.databinding.ItemNewDestinationBinding

class NewDestinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewDestinationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val layoutContainer = binding.layoutInputContainer
        addNewInputRow(layoutContainer)

        binding.fabAddInput.setOnClickListener {
            addNewInputRow(layoutContainer)
        }

        val rowBinding = ItemNewDestinationBinding.inflate(LayoutInflater.from(this))
        rowBinding.destinationTarget.setEndIconOnClickListener {
//            binding.layoutInputContainer.removeView(rowBinding.root)
            Toast.makeText(this, "EndIcon clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addNewInputRow(container: LinearLayout) {
        val rowInput = LayoutInflater.from(this)
            .inflate(R.layout.item_new_destination, container, false)

        container.addView(rowInput)
    }
}