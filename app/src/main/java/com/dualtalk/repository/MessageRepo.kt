package com.dualtalk.repository

import android.util.Log
import com.dualtalk.common.Constant
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageRepo {
    private val database: FirebaseFirestore = Firebase.firestore

    fun sendMessage(message: String) {
        val model = hashMapOf(
            "sendId" to Constant.sendId,
            "receiveId" to Constant.receiveId,
            "message" to message,
            "createdAt" to FieldValue.serverTimestamp()
        )

        database.collection("messages")
            .add(model)
            .addOnSuccessListener { documentReference ->
                Log.d("Send message", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Send message", "Error adding document", e)
            }
    }

    fun createChat(receiveId: String, receiveName: String) {
        val participantIds: ArrayList<String> = arrayListOf(Constant.sendId, receiveId)
        participantIds.sort()
        val participantNames: ArrayList<String> = ArrayList()
        if (participantIds[0] == Constant.sendId) {
            participantNames.add(Constant.sendName)
            participantNames.add(receiveName)
        } else {
            participantNames.add(Constant.sendName)
            participantNames.add(receiveName)
        }

        val model = hashMapOf(
            "participantIds" to participantIds,
            "participantNames" to participantNames,
            "latestMessage" to "",
            "updatedAt" to FieldValue.serverTimestamp()
        )

        database.collection("chats")
            .add(model)
            .addOnSuccessListener { documentReference ->
                Log.d("Create chat", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Create chat", "Error adding document", e)
            }
    }
}