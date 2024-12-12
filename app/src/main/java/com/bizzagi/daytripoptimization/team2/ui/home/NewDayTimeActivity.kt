package com.bizzagi.daytripoptimization.team2.ui.home

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.Result
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.request.Point
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.databinding.ActivityNewDayTimeBinding
import com.bizzagi.daytripoptimization.team2.di.Injection
import com.bizzagi.daytripoptimization.team2.ui.result.TripResultActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                    val result = clusteringRepository.getRecommendation(clusteringRequest)
                    when(result) {
                        is Result.Success -> {
                            Log.d("NewDayTimeActivity", "Input: ${totalDays}")
                            Log.d("NewDayTimeActivity", "Rekomendasi: ${result.data.recommended_days}")

                            if (totalDays != result.data.recommended_days) {
                                MaterialAlertDialogBuilder(this@NewDayTimeActivity)
                                    .setTitle("Reminder")
                                    .setIcon(R.drawable.ic_reminder)
                                    .setMessage("RECOMMENDATION: ${result.data.recommended_days} Days \n\nYour plan might not be optimal.\nAre you sure?")
                                    .setPositiveButton("Yes, Proceed") { dialog, which ->
                                        // Handle the positive button action
                                        lifecycleScope.launch {
                                            try {
                                                val newResult = clusteringRepository.getClusteringRecommendation(clusteringRequest)

                                                handleApiResponse(newResult, clusteringRequest)
                                            } catch (e: Exception) {
                                                // Log pengecualian
                                                Log.e("NewDayTimeActivity", "Exception occurred while calling API: ${e.message}", e)
                                                Toast.makeText(this@NewDayTimeActivity, "An error occurred while calling the API", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        dialog.dismiss()
                                    }
                                    .setNegativeButton("Cancel") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            } else {
                                handleApiResponse(result, clusteringRequest)
                            }
                        }
                        is Result.Error -> {
                            // Log error message
                            Log.e("NewDayTimeActivity", "Error occurred: ${result.error}")
                            // Show error message
                            Toast.makeText(this@NewDayTimeActivity, "An error occurred: ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                        Result.Loading -> {
                            // Handle loading state if needed
                            Log.d("NewDayTimeActivity", "Loading data...")
                        }
                    }
                } catch (e: Exception) {
                    // Log pengecualian
                    Log.e("NewDayTimeActivity", "Exception occurred while calling API: ${e.message}", e)
                    Toast.makeText(this@NewDayTimeActivity, "An error occurred while calling the API", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // get coordinate latitude and longitude
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

    private fun handleApiResponse(result: Result<ClusteringResponse>, clusteringRequest: ClusteringRequest) {
        when (result) {
            is Result.Success -> {
                val intent = Intent(this, TripResultActivity::class.java).apply {
                    putExtra("CLUSTER_DATA", result.data)
                    putExtra("CLUSTER_DESTINATION", clusteringRequest)
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

    // show custom modal dialog to pick time
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