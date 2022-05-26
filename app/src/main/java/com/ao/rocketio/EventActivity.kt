package com.ao.rocketio

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ao.rocketio.api.RetrofitInstance
import com.ao.rocketio.data.BEUser
import com.ao.rocketio.data.Data
import com.ao.rocketio.data.UserRepositoryInDB
import com.ao.rocketio.databinding.ActivityEventBinding
import com.ao.rocketio.enums.EventTypes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.HttpException
import java.io.IOException

const val TAGEVENT = "EventActivity"

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityEventBinding
    private var dataFromApi: Data? = null
    private var eventType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserRepositoryInDB.initialize(this)

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
                    eventType = EventTypes.Wildfire.toString()
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

        var height = 50
        var width = 50
        var icon: Bitmap? = null
        if (eventType?.equals(EventTypes.Wildfire.toString()) == true){
            icon = BitmapFactory.decodeResource(this.resources, R.drawable.fire)
        }else {
            icon = BitmapFactory.decodeResource(this.resources, R.drawable.volcano_icon)
        }

        var minimize = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(icon, width, height, false))

        //re-check if data is not null
        if (dataFromApi != null) {
            //for all events in api data marker is placed on the map
            for (event in dataFromApi!!.events){
                val markerPosition = LatLng(event.geometries[0].coordinates[1], event.geometries[0].coordinates[0])
                mMap.addMarker(MarkerOptions()
                    .position(markerPosition)
                    .title(event.title)
                    .icon(minimize)
                )
            }

            var position: LatLng? = null

            try {
                // movoes camera to the position
                val mRep = UserRepositoryInDB.get()
                val getUserObserver = Observer<BEUser> { user ->
                    position = LatLng(user.latitude,user.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position!!))
                }
                mRep.getById(1).observe(this, getUserObserver)
            }catch (e: Exception) {
                //Easv location
                position = LatLng(55.487724007387115, 8.44689373151934)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position!!))
            }
        }
    }
}