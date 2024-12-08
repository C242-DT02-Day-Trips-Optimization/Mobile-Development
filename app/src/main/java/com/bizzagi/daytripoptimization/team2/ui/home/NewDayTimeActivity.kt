package com.bizzagi.daytripoptimization.team2.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDayTimeBinding

class NewDayTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewDayTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewDayTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbarDayTime
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}