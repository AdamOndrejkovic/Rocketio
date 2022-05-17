package com.ao.rocketio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.rocketio.R
import kotlinx.android.synthetic.main.activity_no_internet.*

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        // retry button that will change activity to main
        btnRetry.setOnClickListener{ _ ->
            Intent(this@NoInternetActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}