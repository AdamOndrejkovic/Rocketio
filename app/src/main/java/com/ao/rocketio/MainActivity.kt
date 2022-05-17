package com.ao.rocketio

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.rocketio.data.BEUser
import com.ao.rocketio.data.UserRepositoryInDB
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize database
        UserRepositoryInDB.initialize(this)
        /*val mRep = UserRepositoryInDB.get()
        mRep.insert(BEUser(0, "John Doe", "Captain", 55.485527, 8.425100 ))*/

        val hasNetwork = checkInternetConnection(this)
        if (hasNetwork){
            //Animation loader
            Timer().schedule(2000) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else {
            val intent = Intent(this@MainActivity, NoInternetActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkInternetConnection(context: Context): Boolean {

        // connectivity manager service with registered activity
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // checks if the android version is equal or greater to M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // return currently active default data network
            val network = connectivityManager.activeNetwork  ?: return false

            // representation of active network and it's capabilitites
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // if network uses Wi-Fi transport
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // if network uses Cellular transport
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // if the android version is below M
                else -> false
            }
        } else {
            // if the android is below M
            @Suppress("DEPRICATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false

            @Suppress("DEPRICATION") return networkInfo.isConnected
        }
    }
}