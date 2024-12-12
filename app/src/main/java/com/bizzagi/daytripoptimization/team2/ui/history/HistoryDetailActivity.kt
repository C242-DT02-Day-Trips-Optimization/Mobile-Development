package com.bizzagi.daytripoptimization.team2.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizzagi.daytripoptimization.team2.MainActivity
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.adapter.ClusterAdapter
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.database.TripResultDatabase
import com.bizzagi.daytripoptimization.team2.databinding.ActivityHistoryDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HistoryDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityHistoryDetailBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var db: TripResultDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarHistory.setNavigationOnClickListener {
            // Start MainActivity when the back button is clicked
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish the current activity if you want to remove it from the back stack
        }

        // Show ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        val clusterDataJson = intent.getStringExtra("CLUSTER_DATA")
        val clusterRequestJson = intent.getStringExtra("CLUSTER_DESTINATION")

        if (clusterDataJson != null && clusterRequestJson != null) {
            lifecycleScope.launch {
                try {
                    // Simulate data retrieval
                    val gson = Gson()
                    val clusterData = gson.fromJson(clusterDataJson, ClusteringResponse::class.java)
                    val clusterRequest = gson.fromJson(clusterRequestJson, ClusteringRequest::class.java)

                    // Display data
                    displayData(clusterData, clusterRequest)

                    // Initialize the map
                    val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.resultMap) as? SupportMapFragment
                    mapFragment?.getMapAsync(this@HistoryDetailActivity)

                    // Initialize Room Database
                    db = TripResultDatabase.getDatabase(this@HistoryDetailActivity)

                    // Handle delete button click
                    binding.btnDelete.setOnClickListener {
                        val id = intent.getStringExtra("ID") // Assuming the ID is passed as an extra
                        if (id != null) {
                            deleteData(id) // Call delete function with ID
                        } else {
                            Toast.makeText(this@HistoryDetailActivity, "ID not available", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    // Log and handle any errors
                    Toast.makeText(this@HistoryDetailActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                    Log.e("HistoryDetailActivity", "Error: ${e.message}", e)
                } finally {
                    // Hide ProgressBar
                    binding.progressBar.visibility = View.GONE
                }
            }
        } else {
            Toast.makeText(this, "Data is not available!", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            finish()
        }
    }

    private fun deleteData(id: String) {
        lifecycleScope.launch {
            val tripHistoryToDelete = db.tripHistoryDao().getTripHistoryById(id)
            if (tripHistoryToDelete != null) {
                db.tripHistoryDao().deleteTripHistory(tripHistoryToDelete)
                // Show success message
                Toast.makeText(this@HistoryDetailActivity, "Data deleted successfully!", Toast.LENGTH_SHORT).show()

                // Send a broadcast to notify the fragment to refresh the data
                val intent = Intent(this@HistoryDetailActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@HistoryDetailActivity, "Data not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayData(clusterData: ClusteringResponse, clusterRequest: ClusteringRequest) {
        // Membuat adapter untuk RecyclerView
        val adapter = ClusterAdapter(clusterData.grouped_clusters)

        // Mengatur layout manager untuk RecyclerView
        binding.viewDayTrip.layoutManager = LinearLayoutManager(this)

        // Mengatur adapter untuk RecyclerView
        binding.viewDayTrip.adapter = adapter
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Mendapatkan data koordinat dari clusterRequest untuk menampilkan marker
        val clusterRequestJson = intent.getStringExtra("CLUSTER_DESTINATION")
        if (clusterRequestJson != null) {
            val clusterRequest = Gson().fromJson(clusterRequestJson, ClusteringRequest::class.java)
            val locations = mutableListOf<Pair<LatLng, String>>()

            // Mengambil data lat dan long dari clusterRequest untuk setiap point
            for (point in clusterRequest.points) {
                val latLng = LatLng(point.coordinates[0], point.coordinates[1])
                locations.add(Pair(latLng, point.name))
            }

            // Menambahkan marker pada peta
            val boundsBuilder = LatLngBounds.Builder()
            for (location in locations) {
                addMarker(location.first, location.second)
                boundsBuilder.include(location.first) // Menambahkan lokasi ke bounds untuk memperbesar peta
            }

            // Menyesuaikan kamera ke bounds untuk memastikan semua marker terlihat
            val bounds = boundsBuilder.build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 180))
        }
    }

    private fun addMarker(position: LatLng, title: String) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }
}
