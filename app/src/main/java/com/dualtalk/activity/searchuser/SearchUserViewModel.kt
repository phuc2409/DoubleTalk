package com.dualtalk.activity.searchuser

import android.util.Log
import android.widget.Toast
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchUserViewModel : ViewModel(), Observable {
    sealed class SearchUserState() {
        object Success : SearchUserState()
        object Fail : SearchUserState()
    }

    var uiState = MutableLiveData<SearchUserState>()
    var mlist = ArrayList<MUser>()
    val db = Firebase.firestore

    fun getListUser() {
        db.collection("users").get().addOnSuccessListener { result ->
            for (user in result) {
                val email = user.getString("email").toString()
                val name = user.getString("fullName").toString()
                val url = user.getString("url").toString()
                mlist.add(MUser(email, name, url))
            }
            uiState.postValue(SearchUserState.Success)
            Log.d("mlist", "Đã có size = ${mlist.size}")
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}