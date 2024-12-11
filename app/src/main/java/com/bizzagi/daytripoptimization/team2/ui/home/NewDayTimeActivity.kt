package com.bizzagi.daytripoptimization.team2.ui.home

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bizzagi.daytripoptimization.team2.Result
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.request.Point
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDayTimeBinding
import com.bizzagi.daytripoptimization.team2.di.Injection
import com.bizzagi.daytripoptimization.team2.ui.result.TripResultActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
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
            finish()
        }

        // Ambil provinsi dan destinasi dari intent
        val province = intent.getStringExtra("PROVINCE") ?: ""
        val destinations = intent.getStringArrayListExtra("DESTINATIONS") ?: arrayListOf()

        // Log untuk memeriksa provinsi dan destinasi
        Log.d("NewDayTimeActivity", "Received province: $province") // Log untuk debugging
        Log.d("NewDayTimeActivity", "Received destinations: $destinations") // Log untuk debugging

        // Set OnClickListener untuk Start Time
        binding.valueStartTime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                binding.valueStartTime.setText(selectedTime)
            }
        }

        // Set OnClickListener untuk End Time
        binding.valueEndTime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                binding.valueEndTime.setText(selectedTime)
            }
        }

        binding.btnGenerate.setOnClickListener {
            val totalDays = binding.valueTotalDays.text.toString().toIntOrNull() ?: 0
            val startTime = binding.valueStartTime.text.toString()
            val endTime = binding.valueEndTime.text.toString()

            // Validasi input
            if (totalDays <= 0) {
                Toast.makeText(this, "Total days must be greater than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Dapatkan koordinat untuk destinasi
            val points = getCoordinatesForDestinations(destinations)

            // Buat permintaan clustering
            val clusteringRequest = ClusteringRequest(
                points = points,
                num_clusters = totalDays,
                province = province,
                daily_start_time = startTime,
                daily_end_time = endTime
            )

            // Log data permintaan
            Log.d("NewDayTimeActivity", "ClusteringRequest: $clusteringRequest")

            // Panggil API clustering
            val clusteringRepository = Injection.provideClusteringRepository()
            lifecycleScope.launch {
                try {
                    val result = clusteringRepository.getClusteringRecommendation(clusteringRequest)
                    handleApiResponse(result)
                } catch (e: Exception) {
                    // Log pengecualian
                    Log.e("NewDayTimeActivity", "Exception occurred while calling API: ${e.message}", e)
                    Toast.makeText(this@NewDayTimeActivity, "An error occurred while calling the API", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCoordinatesForDestinations(destinations: List<String>): List<Point> {
        val geocoder = Geocoder(this)
        val points = mutableListOf<Point>()

        for (destination in destinations) {
            val addressList = geocoder.getFromLocationName(destination, 1)
            if (addressList != null) {
                if (addressList.isNotEmpty()) {
                    val address = addressList[0]
                    val coordinates = listOf(address.latitude, address.longitude)
                    points.add(Point(destination, coordinates))
                }
            }
        }
        return points
    }

    private fun handleApiResponse(result: Result<ClusteringResponse>) {
        when (result) {
            is Result.Success -> {
                val intent = Intent(this, TripResultActivity::class.java).apply {
                    putExtra("CLUSTER_DATA", result.data)
                }
                startActivity(intent)
            }
            is Result.Error -> {
                // Log error message
                Log.e("NewDayTimeActivity", "Error occurred: ${result.error}")
                // Show error message
                Toast.makeText(this, "An error occurred: ${result.error}", Toast.LENGTH_SHORT).show()
            }
            Result.Loading -> {
                // Handle loading state if needed
                Log.d("NewDayTimeActivity", "Loading data...")
            }
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