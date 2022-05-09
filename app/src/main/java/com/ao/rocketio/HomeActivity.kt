package com.ao.rocketio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.rocketio.R
import com.ao.rocketio.enums.EventTypes
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnImage.setOnClickListener { _ ->
            Intent(this , MarsActivity::class.java ).also {
                startActivity(it)
            }
        }

        btnWildfire.setOnClickListener { _ ->
            Intent(this , EventActivity::class.java ).also {
                it.putExtra("EVENT_TYPE", EventTypes.Wildfire)
                startActivity(it)
            }
        }

        btnVolcano.setOnClickListener { _ ->
            Intent(this, EventActivity::class.java ).also {
                it.putExtra("EVENT_TYPE", EventTypes.Wildfire)
                startActivity(it)
            }
        }

        btnMars.setOnClickListener { _ ->
            Intent(this , MarsActivity::class.java ).also {
                startActivity(it)
            }
        }
    }
}