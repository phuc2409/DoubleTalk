package com.dualtalk.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.CurrentUser
import com.dualtalk.receiver.NotificationReceiver
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class NewMessageService : Service() {
    private val db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.M)
    private val listenerRegistration =
        db.collection("chats").whereArrayContains("participantIds", CurrentUser.id)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Chat Service", "listen:error", e)
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    if (dc.type == DocumentChange.Type.MODIFIED && dc.document.data["sendId"].toString() != CurrentUser.id) {
                        Log.d("Chat Service", "New: ${dc.document.data}")

                        val chatModel = ChatModel(
                            dc.document.id,
                            dc.document.data["participantIds"] as ArrayList<String>,
                            dc.document.data["participantNames"] as ArrayList<String>,
                            dc.document.data["participantImgUrls"] as ArrayList<String>,
                            dc.document.data["sendId"].toString(),
                            dc.document.data["sendName"].toString(),
                            dc.document.data["latestMessage"].toString()
                        )
                        val gson = Gson()
                        val json: String = gson.toJson(chatModel)

                        val intent = Intent(this, NotificationReceiver::class.java)
                        intent.putExtra("json", json)
                        val pendingIntent: PendingIntent =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                PendingIntent.getBroadcast(
                                    this,
                                    chatModel.id.hashCode(),
                                    intent,
                                    PendingIntent.FLAG_MUTABLE
                                )
                            } else {
                                PendingIntent.getBroadcast(this, chatModel.id.hashCode(), intent, 0)
                            }
                        val action = NotificationCompat.Action.Builder(
                            R.drawable.ic_message,
                            "Open",
                            pendingIntent
                        ).build()

                        val builder =
                            NotificationCompat.Builder(this, getString(R.string.channel_id))
                                .setSmallIcon(R.drawable.ic_message)
                                .setContentTitle(chatModel.sendName)
                                .setContentText(chatModel.latestMessage)
                                .setPriority(NotificationCompat.PRIORITY_MAX)
                                .addAction(action)

                        NotificationManagerCompat.from(this)
                            .notify(dc.document.id.hashCode(), builder.build())
                    }
                }
            }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val builder =
            NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Double Talk")
                .setContentText(CurrentUser.fullName)
                .setPriority(NotificationCompat.PRIORITY_MIN)
        startForeground(100, builder.build())
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration.remove()
    }
}