package com.dualtalk.fragment.setting

import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.fragment.all_chat.AllChatViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class SettingFragmentViewModel : ViewModel() , Observable {

    sealed class ChooseImageAvatar{
        object Success : ChooseImageAvatar()
        object Fail : ChooseImageAvatar()
    }

    val uiState = MutableLiveData<ChooseImageAvatar>()




    fun UpLoadAvartarToClound(uri: Uri){
        val storage = FirebaseStorage.getInstance().getReference("Picture/$uri")
        storage.putFile(uri).addOnSuccessListener {
            uiState.postValue(ChooseImageAvatar.Success)
        }.addOnFailureListener{
            uiState.postValue(ChooseImageAvatar.Fail)
        }
    }







    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}