package com.dualtalk.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * User: Quang Phúc
 * Date: 15-May-22
 * Time: 09:54 PM
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var json = ""
        intent?.let {
            json = it.getStringExtra("json").toString()
        }
        if (json.isNotBlank()) {
            val chatIntent = Intent("com.dualtalk.OPEN_CHAT")
            chatIntent.putExtra("json", json)
            context?.sendBroadcast(chatIntent)
        }
    }
}