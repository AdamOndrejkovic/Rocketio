package com.ao.rocketio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ao.rocketio.R
import com.ao.rocketio.api.RetrofitInstance
import com.ao.rocketio.data.Weather
import com.ao.rocketio.databinding.ActivityMarsBinding
import com.ao.rocketio.enums.MediaEnum
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.activity_mars.*
import retrofit2.HttpException
import java.io.IOException

class MarsActivity : AppCompatActivity() {

    private var marsWatherData: Weather? = null
    private lateinit var binding: ActivityMarsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_mars)

        // corutine that starts when activity is created and is destroyed when cancelled
        // corutine tries to get data from api
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.insight.getData()
            } catch (e: IOException){
                Log.e(TAGIMAGE, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAGIMAGE, "IOException")
                return@launchWhenCreated
            }

            // if response successfull data is set to activity and displayed
            if (response.isSuccessful && response.body() != null) {
              marsWatherData = response.body()

              // setup of the content within the activity

              tvSolData.text = marsWatherData?.sol.toString()

              tvMinTempData.text = marsWatherData?.min_temp.toString()
              tvMaxTempData.text = marsWatherData?.max_temp.toString()

              tvSunriseData.text = marsWatherData?.sunrise
              tvSunsetData.text = marsWatherData?.sunset

            }else {
                // if case of error user redirected to error activity
                Log.e(TAGIMAGE, "Response not succesfull")
                Intent(this@MarsActivity, ErrorActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}