package com.ao.rocketio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ao.rocketio.api.RetrofitInstance
import com.ao.rocketio.data.Data
import com.ao.rocketio.databinding.ActivityEventBinding
import com.ao.rocketio.enums.EventTypes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.HttpException
import java.io.IOException

const val TAGEVENT = "EventActivity"

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityEventBinding
    private var dataFromApi: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retreive extras including the event type
        val extras = intent.getStringExtra("EVENT_TYPE")

        // Get a handle to the fragment and register the callback
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Canceled when app in background
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                // api call based on the event type
                if (extras?.equals(EventTypes.Wildfire.toString()) == true){
                    RetrofitInstance.eonet.getWildfires()
                }else {
                    RetrofitInstance.eonet.getVolcanos()
                }
            } catch (e: IOException){
                Log.e(TAGEVENT, "IOException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAGEVENT, "IOException")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            Log.e(TAGEVENT, response.message())
            Log.e(TAGEVENT, response.isSuccessful.toString())
            if (response.isSuccessful && response.body() != null) {
                //!! is not null
                dataFromApi = response.body()!!
                Log.e(TAGEVENT, dataFromApi.toString())
                onMapReady(mMap)
            } else {
                Log.e(TAGEVENT, "Response not succesfull")
                Intent(this@EventActivity, ErrorActivity::class.java).also {
                    startActivity(it)
                }
            }
            binding.progressBar.isVisible = false
        }
    }

    //Get a handle to the GoogleMap Object and display marker
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //re-check if data is not null
        if (dataFromApi != null) {
            //for all events in api data marker is placed on the map
            for (event in dataFromApi!!.events){
                val markerPosition = LatLng(event.geometries[0].coordinates[1], event.geometries[0].coordinates[0])
                mMap.addMarker(MarkerOptions()
                    .position(markerPosition)
                    .title(event.title)
                )
                // movoes camera to the position of marker
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
            }
        }
    }
}