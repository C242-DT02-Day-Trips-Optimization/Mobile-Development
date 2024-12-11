package com.bizzagi.daytripoptimization.team2.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bizzagi.daytripoptimization.team2.R
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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.resultMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Example of custom location
        val locations = listOf(
            Pair(LatLng(-7.2482, 112.7356), "House of Sampoerna"),
            Pair(LatLng(-7.2458, 112.7374), "Tugu Pahlawan"),
            Pair(LatLng(-7.2656, 112.7461), "Submarine Monument")
        )

        // Add markers and build bounds
        val boundsBuilder = LatLngBounds.Builder()
        for (location in locations) {
            addMarker(location.first, location.second)
            boundsBuilder.include(location.first) // Include each location in bounds
        }

        val bounds = boundsBuilder.build()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 180))
    }

    // Function to add marker using lat and long
    private fun addMarker(position: LatLng, title: String) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }
}