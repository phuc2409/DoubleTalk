package com.dualtalk.fragment.setting

import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.fragment.all_chat.AllChatViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

class SettingFragmentViewModel : ViewModel() , Observable {

    sealed class ChooseImageAvatar{
        object Success : ChooseImageAvatar()
        object Fail : ChooseImageAvatar()
    }

    val uiState = MutableLiveData<ChooseImageAvatar>()

     var urlUserAvartar : String = "123213"



    fun UpLoadAvartarToClound(uri: Uri){
        var storage = FirebaseStorage.getInstance().getReference("Picture/$uri")
        storage.putFile(uri).addOnSuccessListener {
            uiState.postValue(ChooseImageAvatar.Success)
        }.addOnFailureListener{
            uiState.postValue(ChooseImageAvatar.Fail)
        }
        storage = Firebase.storage.reference
        storage.child("Picture/$uri").downloadUrl.addOnSuccessListener {
            this.urlUserAvartar = it.toString()
        }
    }







    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}