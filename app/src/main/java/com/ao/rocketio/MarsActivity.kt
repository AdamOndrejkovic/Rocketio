package com.ao.rocketio

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

        supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

            if (response.isSuccessful && response.body() != null) {
              marsWatherData = response.body()

              tvSolData.text = marsWatherData?.sol.toString()

              tvMinTempData.text = marsWatherData?.min_temp.toString()
              tvMaxTempData.text = marsWatherData?.max_temp.toString()

              tvSunriseData.text = marsWatherData?.sunrise
              tvSunsetData.text = marsWatherData?.sunset

            }else {
                Log.e(TAGIMAGE, "Response not succesfull")
            }
        }
    }
}