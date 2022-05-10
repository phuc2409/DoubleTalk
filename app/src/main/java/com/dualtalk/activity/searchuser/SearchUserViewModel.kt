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
                val email = user.getString("email").toString().trim()
                val name = user.getString("fullName").toString().trim()
                val url = user.getString("imgUrl").toString().trim()
                val id = user.getString("id").toString().trim()
                mlist.add(MUser(id ,email, name, url))
            }
            mlist.add(MUser("1" , "dat@gmail.com" , "Đạt Nguyễn" , "https://firebasestorage.googleapis.com/v0/b/demo2021-ae092.appspot.com/o/Picture%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A24?alt=media&token=3a149aa5-c97e-4858-a295-a5a9e59b3399"))
            uiState.postValue(SearchUserState.Success)
            Log.d("mlist", "Đã có size = ${mlist.size}")
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}