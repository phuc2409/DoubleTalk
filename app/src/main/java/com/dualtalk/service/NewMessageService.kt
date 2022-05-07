package com.dualtalk.service

import com.dualtalk.R
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewMessageService : Service() {
    private val db = Firebase.firestore

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        db.collection("chats").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("Chat Service", "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                if (dc.type == DocumentChange.Type.MODIFIED) {
                    Log.d("Chat Service", "New: ${dc.document.data}")
//                    createNotificationChannel(dc.document.id)
                    val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
                        .setSmallIcon(R.drawable.ic_send)
                        .setContentTitle(dc.document.data["participantNames"].toString())
                        .setContentText(dc.document.data["latestMessage"].toString())
                        .setPriority(NotificationCompat.PRIORITY_MAX)

                    NotificationManagerCompat.from(this).notify(dc.document.id.hashCode(), builder.build())
//                        DocumentChange.Type.MODIFIED -> Log.d("Chat Service", "Modified: ${dc.document.data}")
//                        DocumentChange.Type.REMOVED -> Log.d("Chat Service", "Removed: ${dc.document.data}")
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }
}