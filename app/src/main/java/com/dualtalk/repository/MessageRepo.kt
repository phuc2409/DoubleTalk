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
}