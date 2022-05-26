package com.ao.rocketio

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

//Settings activity with possibility to change languages (*Not implemented)
class SettingsActivity : AppCompatActivity() {

    enum class Languages () {
        English,
        Chinese,
        Dutch,
        French,
        German,
        Danish,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    private fun updateLanguage(language: SettingsActivity.Languages) {
        val languageToLoad = "fr" // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
        Intent(this@SettingsActivity, HomeActivity::class.java ).also {
            startActivity(it)
        }

    }
}