package com.ao.rocketio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.rocketio.R
import kotlinx.android.synthetic.main.activity_error.*

class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        // go home button that will change activity to home
        btnHome.setOnClickListener{ _ ->
            Intent(this@ErrorActivity, HomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}