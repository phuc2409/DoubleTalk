package com.dualtalk.activity.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dualtalk.R
import com.dualtalk.fragment.setting.SettingFragment
import com.dualtalk.fragment.TinnhanchoFragment
import com.dualtalk.fragment.all_chat.AllChatFragment
import com.dualtalk.service.NewMessageService
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val chat = AllChatFragment()
        val tinnhancho = TinnhanchoFragment()
        val setting = SettingFragment()

        makeCurrentFragment(chat)

        val intent = Intent(this, NewMessageService::class.java)
        startService(intent)

        val bottomnavigation = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        bottomnavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.doanchat -> makeCurrentFragment(chat)
                R.id.tincho -> makeCurrentFragment(tinnhancho)
                R.id.settings -> makeCurrentFragment(setting)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(getString(R.string.channel_id), name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}