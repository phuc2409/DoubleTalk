package com.dualtalk.activity.splashscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatActivity
import com.dualtalk.activity.login.LoginActivity
import com.dualtalk.activity.main.MainActivity
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var auth = FirebaseAuth.getInstance()

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

        preferences = getSharedPreferences("share",Context.MODE_PRIVATE)
        val username = preferences.getString("User","")
        val pass = preferences.getString("Pass","")
        if(username.isNullOrEmpty())
        {
            var intent: Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(this,"Thành Công ",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))

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