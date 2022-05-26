package com.ao.rocketio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ao.rocketio.R
import com.ao.rocketio.enums.EventTypes
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // starts the image activity
        btnImage.setOnClickListener { _ ->
            Intent(this@HomeActivity, ImageActivity::class.java ).also {
                startActivity(it)
            }
        }

        // starts the event activity with event type of wildfire
        btnWildfire.setOnClickListener { _ ->
            Intent(this@HomeActivity , EventActivity::class.java ).also {
                it.putExtra("EVENT_TYPE", EventTypes.Wildfire.toString())
                startActivity(it)
            }
        }

        // starts the event activity with event type of volcanos
        btnVolcano.setOnClickListener { _ ->
            Intent(this@HomeActivity, EventActivity::class.java ).also {
                it.putExtra("EVENT_TYPE", EventTypes.Volcanos)
                startActivity(it)
            }
        }

        // starts the mars weather activity
        btnMars.setOnClickListener { _ ->
            Intent(this@HomeActivity , MarsActivity::class.java ).also {
                startActivity(it)
            }
        }
    }

    //Used to instantiate menu XML into Menu objects
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        //Error occours when using androidx.core...
        inflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    // menu options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iProfile -> {
                Intent(this, ProfileActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.iSettings -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.iAbout -> {
                Intent(this, AboutActivity::class.java).also {
                    startActivity(it)
                }
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}