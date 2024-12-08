package com.bizzagi.daytripoptimization.team2.ui.home

import android.content.Intent
import android.os.Bundle
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
        addNewInputRow()

        val toolbar: Toolbar = binding.toolbarDestination
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.fabAddInput.setOnClickListener {
            addNewInputRow()
        }

        binding.btnDateTime.setOnClickListener {
            val intent = Intent(this, NewDayTimeActivity::class.java)

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