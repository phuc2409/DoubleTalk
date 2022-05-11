package com.dualtalk.repository

import android.util.Log
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.CurrentUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageRepo {
    private val database: FirebaseFirestore = Firebase.firestore

    fun sendMessage(chatModel: ChatModel, message: String) {
        val model = hashMapOf(
            "chatId" to chatModel.id,
            "sendId" to CurrentUser.id,
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

    fun createChat(
        chatModel: ChatModel,
        onSuccess: (newChatId: String) -> Unit,
        onError: () -> Unit
    ) {
        val model = hashMapOf(
            "participantIds" to chatModel.participantIds,
            "participantNames" to chatModel.participantNames,
            "sendId" to CurrentUser.id,
            "sendName" to CurrentUser.fullName,
            "latestMessage" to "",
            "updatedAt" to FieldValue.serverTimestamp()
        )

        database.collection("chats").add(model)
            .addOnSuccessListener {
                Log.d("Add chat", "DocumentSnapshot added with ID: ${it.id}")
                onSuccess(it.id)
            }
            .addOnFailureListener { e ->
                Log.e("Add chat", "Error adding document", e)
                onError()
            }
    }

    fun updateChat(chatModel: ChatModel, message: String) {
        val model = hashMapOf(
            "participantIds" to chatModel.participantIds,
            "participantNames" to chatModel.participantNames,
            "sendId" to CurrentUser.id,
            "sendName" to CurrentUser.fullName,
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