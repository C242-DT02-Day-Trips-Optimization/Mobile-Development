package com.bizzagi.daytripoptimization.team2.ui.home

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDayTimeBinding
import com.bizzagi.daytripoptimization.team2.ui.result.TripResultActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

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

        val startTime = binding.valueStartTime
        val endTime = binding.valueEndTime

        startTime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                startTime.setText(selectedTime)
            }
        }

        endTime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                endTime.setText(selectedTime)
            }
        }

        binding.btnGenerate.setOnClickListener {
            val intent = Intent(this, TripResultActivity::class.java)

            startActivity(intent)
        }
    }

    private fun showTimePickerDialog(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create the MaterialTimePicker with 24-hour format
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText("Select Time")
            .build()

        picker.show(supportFragmentManager, "MaterialTimePicker")

        // Handle the result
        picker.addOnPositiveButtonClickListener {
            val selectedTime = String.format("%02d:%02d", picker.hour, picker.minute) // 24-hour format
            onTimeSelected(selectedTime)
        }
    }
}