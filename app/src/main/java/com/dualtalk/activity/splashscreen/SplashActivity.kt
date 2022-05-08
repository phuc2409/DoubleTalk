package com.dualtalk.activity.splashscreen

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatActivity

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(isConnected() == false)
        {
            Toast.makeText(this,"You're not connected to internet \n Please check your connection",
                Toast.LENGTH_SHORT).show()

            handler = Handler()
            handler.postDelayed(
                {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    finish()
                },2000
            )
        }
        else
        {
            handler = Handler()
            handler.postDelayed(
                {
                    val intent = Intent(this,ChatActivity::class.java)
                    startActivity(intent)
                    finish()
                },2000
            )
        }

    }
    fun  isConnected() : Boolean
    {
        var connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var network: NetworkInfo? = connectivityManager.activeNetworkInfo
        if(network!=null)
        {
            if(network.isConnected)
            {
                return true
            }
        }
        return false
    }
}