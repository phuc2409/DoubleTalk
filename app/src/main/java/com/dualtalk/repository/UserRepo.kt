package com.dualtalk.repository

import android.util.Log
import com.dualtalk.activity.searchuser.MUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepo {
    private val database: FirebaseFirestore = Firebase.firestore

    fun getCurrentUser(onSuccess: (MUser) -> Unit, onError: () -> Unit) {
        Firebase.auth.currentUser?.let {
            database.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val user = MUser(
                            document.id,
                            document.getString("email"),
                            document.getString("fullName"),
                            document.getString("imgUrl")
                        )
                        Log.d("Get current user", "DocumentSnapshot data: ${document.data}")
                        onSuccess(user)
                    } else {
                        Log.d("Get current user", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Get current user", "Get failed with ", exception)
                }
        } ?: run {
            Log.d("Get current user", "Get failed with currentUser = null")
            onError()
        }
    }
}