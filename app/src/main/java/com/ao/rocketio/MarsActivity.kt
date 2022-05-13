package com.ao.rocketio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.rocketio.R

class MarsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars)

        supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}