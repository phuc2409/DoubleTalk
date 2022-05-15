package com.dualtalk.activity.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatActivity
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.fragment.setting.SettingFragment
import com.dualtalk.fragment.all_chat.AllChatFragment
import com.dualtalk.service.NewMessageService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val gson = Gson()
            val chatModel = gson.fromJson(intent.getStringExtra("json"), ChatModel::class.java)
            openChat(chatModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        createNotificationChannel()

        registerReceiver(receiver, IntentFilter("com.dualtalk.OPEN_CHAT"))

        viewModel.uiState.observe(this) {
            if (it == MainViewModel.MainState.Success) {
                setupView()
            }
        }
    }

    private fun setupView() {
        val chat = AllChatFragment()
        val setting = SettingFragment()

        makeCurrentFragment(chat)

        val intent = Intent(this, NewMessageService::class.java)
        startService(intent)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.doanchat -> makeCurrentFragment(chat)
                R.id.settings -> makeCurrentFragment(setting)
            }
            true
        }
    }

    private fun openChat(item: ChatModel?) {
        val intent = Intent(this, ChatActivity::class.java)
        val gson = Gson()
        val json: String = gson.toJson(item)
        intent.putExtra("json", json)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
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