package com.bizzagi.daytripoptimization.team2.ui.result

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.adapter.ClusterAdapter
import com.bizzagi.daytripoptimization.team2.data.request.ClusteringRequest
import com.bizzagi.daytripoptimization.team2.data.response.ClusteringResponse
import com.bizzagi.daytripoptimization.team2.databinding.ActivityTripResultBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class TripResultActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityTripResultBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTripResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarResult.setNavigationOnClickListener {
            finish()
        }

        // Mendapatkan data dari intent
        val clusterData = intent.getSerializableExtra("CLUSTER_DATA") as ClusteringResponse
        val clusterRequest = intent.getSerializableExtra("CLUSTER_DESTINATION") as ClusteringRequest

        Log.d("TripResultActivity", "$clusterData")

        // Membuat adapter untuk RecyclerView
        val adapter = ClusterAdapter(clusterData.grouped_clusters)

        // Membuat RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.viewDayTrip)

        // Mengatur layout manager untuk RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengatur adapter untuk RecyclerView
        recyclerView.adapter = adapter

        // Inisialisasi SupportMapFragment untuk peta
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.resultMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Mendapatkan data koordinat dari clusterRequest untuk menampilkan marker
        val clusterRequest = intent.getSerializableExtra("CLUSTER_DESTINATION") as ClusteringRequest
        val locations = mutableListOf<Pair<LatLng, String>>()

        // Mengambil data lat dan long dari clusterRequest untuk setiap point
        for (point in clusterRequest.points) {
            val lat = point.coordinates[0] // Latitude
            val long = point.coordinates[1] // Longitude
            val title = point.name

            // Menambahkan lokasi dan nama ke list
            locations.add(Pair(LatLng(lat, long), title))
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

    // Fungsi untuk menambahkan marker pada peta
    private fun addMarker(position: LatLng, title: String) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }
}
