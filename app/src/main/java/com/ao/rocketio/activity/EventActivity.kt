package com.ao.rocketio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ao.rocketio.R
import com.ao.rocketio.data.Data
import com.ao.rocketio.databinding.ActivityEventBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityEventBinding
    private var dataFromApi: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get a handle to the fragment and register the callback
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    //Get a handle to the GoogleMap Object and display marker
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (dataFromApi != null) {
            for (event in dataFromApi!!.events){
                val markerPosition = LatLng(event.geometries[0].coordinates[1], event.geometries[0].coordinates[0])
                mMap.addMarker(MarkerOptions()
                    .position(markerPosition)
                    .title(event.title)
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
            }
        }
    }
}