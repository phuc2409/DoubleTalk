package com.dualtalk.fragment.setting

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.activity.searchuser.MUser
import com.dualtalk.fragment.all_chat.AllChatViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

class SettingFragmentViewModel : ViewModel(), Observable {

    sealed class ChooseImageAvatar {
        object Success : ChooseImageAvatar()
        object Fail : ChooseImageAvatar()
        object getinfoSucess : ChooseImageAvatar()
    }

    val uiState = MutableLiveData<ChooseImageAvatar>()

    var urlUserAvartar: String = "123213"
    var infouser = MUser()


    fun UpLoadAvartarToClound(uri: Uri) {
        var storage = FirebaseStorage.getInstance().getReference("Picture/$uri")
        storage.putFile(uri).addOnSuccessListener {
            uiState.postValue(ChooseImageAvatar.Success)
        }.addOnFailureListener {
            uiState.postValue(ChooseImageAvatar.Fail)
        }
        storage = Firebase.storage.reference
        storage.child("Picture/$uri").downloadUrl.addOnSuccessListener {
            this.urlUserAvartar = it.toString()
        }
    }

    fun getInfoUser() {
        val db = Firebase.firestore
        Firebase.auth.currentUser?.let {
            db.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        document.getString("id")?.let {it1 ->
                            infouser.id = it1
                        } ?: run {
                            //neu bi null
                        }
                        document.getString("fullName")?.let {it1->
                            infouser.fullName = it1
                        } ?: run{

                        }
                        document.getString("email")?.let {it1->
                            infouser.email = it1
                        } ?: run{

                        }
                        document.getString("imgUrl")?.let {it1->
                            infouser.imgUrl = it1
                        } ?: run{

                        }
                        uiState.postValue(ChooseImageAvatar.getinfoSucess)
                    } else {

                    }
                }
                .addOnFailureListener { exception ->

                }
        }

        uiState.postValue(ChooseImageAvatar.getinfoSucess)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}