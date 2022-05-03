package com.dualtalk.repository

import android.util.Log
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.Constant
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageRepo {
    private val database: FirebaseFirestore = Firebase.firestore

    fun sendMessage(chatModel: ChatModel, message: String) {
        val model = hashMapOf(
            "chatId" to chatModel.id,
            "sendId" to Constant.sendId,
            "message" to message,
            "createdAt" to FieldValue.serverTimestamp()
        )

        database.collection("messages")
            .add(model)
            .addOnSuccessListener { documentReference ->
                Log.d("Send message", "DocumentSnapshot added with ID: ${documentReference.id}")
                updateChat(chatModel, message)
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

    fun updateChat(chatModel: ChatModel, message: String) {
        val model = hashMapOf(
            "participantIds" to chatModel.participantIds,
            "participantNames" to chatModel.participantNames,
            "latestMessage" to message,
            "updatedAt" to FieldValue.serverTimestamp()
        )

        database.collection("chats").document(chatModel.id).update(model)
            .addOnSuccessListener {
                Log.d("Update chat", "DocumentSnapshot updated with ID: ${chatModel.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Update chat", "Error updating document", e)
            }
    }
}