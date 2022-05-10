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

class SearchUserViewModel: ViewModel() , Observable {
    sealed class search_user_state(){
        object Sucess : search_user_state()
        object Fail : search_user_state()
    }

    var uiState = MutableLiveData<search_user_state>()
    var mlist  = ArrayList<MUser>()
    val db = Firebase.firestore




    fun getListUser() : List<MUser> {
        getDataFromFireStore()
        mlist.add(MUser("", "Đạt" , "https://kenh14cdn.com/thumb_w/660/2020/5/28/0-1590653959375414280410.jpg"))
        uiState.postValue(search_user_state.Sucess)
        Log.d("mlist" , "Đã có size = ${mlist.size}")
        return mlist.toList()

    }

    private fun getDataFromFireStore() {
        db.collectionGroup("users").get().addOnSuccessListener {result ->
            for(user in result){
                val email = user.getString("email").toString()
                val name = user.getString("name").toString()
                val url = user.getString("url").toString()
                mlist.add(MUser(email,name,url))
            }

        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}