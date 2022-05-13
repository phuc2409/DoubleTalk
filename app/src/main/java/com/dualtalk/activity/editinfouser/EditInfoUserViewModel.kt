package com.dualtalk.activity.editinfouser

import android.net.Uri
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.common.CurrentUser
import com.dualtalk.helper.DateTimeHelper
import com.dualtalk.repository.UserRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class EditInfoUserViewModel : ViewModel(), Observable {
    sealed class UpdateInfoState {
        object Success : UpdateInfoState()
        object Fail : UpdateInfoState()
        object UpLoadImageSucess : UpdateInfoState()
        object UpLoadImageFail : UpdateInfoState()
    }

    val updateInfoState = MutableLiveData<UpdateInfoState>()
    val db = Firebase.firestore
    val userRepo = UserRepo()
    lateinit var urlUserAvartar: String
    lateinit var name: String


    fun UpdateInfoUser(name: String) {
        if (!name.isNullOrBlank()) {
            val sfDocRef = db.collection("users").document(CurrentUser.id)
            db.runTransaction { transaction ->
                val snapshot = transaction.get(sfDocRef)
                transaction.update(sfDocRef, "fullName", name)

            }.addOnSuccessListener {
                updateInfoState.postValue(UpdateInfoState.Success)
                this.name = name
            }.addOnFailureListener {
                updateInfoState.postValue(UpdateInfoState.Fail)
            }
        }
        //CurrentUser.fullName = name
    }

    fun UpLoadAvartarToClound(uri: Uri) {
        val now = "${CurrentUser.id}-${DateTimeHelper.getDateTimeNow()}"
        var storage = FirebaseStorage.getInstance().getReference("Picture/$uri-${now}")
        storage.putFile(uri).addOnSuccessListener {
            updateInfoState.postValue(UpdateInfoState.UpLoadImageSucess)

            storage = Firebase.storage.reference
            storage.child("Picture/$uri-${now}").downloadUrl.addOnSuccessListener {
                this.urlUserAvartar = it.toString()
                userRepo.updateImg(urlUserAvartar,
                    onSuccess = {
                        updateInfoState.postValue(UpdateInfoState.UpLoadImageSucess)
                        CurrentUser.imgUrl = urlUserAvartar
                    },
                    onError = {
                        updateInfoState.postValue(UpdateInfoState.UpLoadImageFail)
                    }
                )
            }.addOnFailureListener {
                updateInfoState.postValue(UpdateInfoState.UpLoadImageFail)
            }


            //up date link ảnh cho user ở trong firestore
//            val sfDocRef = db.collection("users").document(CurrentUser.id)
//            db.runTransaction { transaction ->
//                val snapshot = transaction.get(sfDocRef)
//                transaction.update(sfDocRef, "imgUrl", this.urlUserAvartar)
//            }.addOnSuccessListener {
//
//            }.addOnFailureListener {
//
//            }


        }.addOnFailureListener {
            updateInfoState.postValue(UpdateInfoState.Fail)
        }


        //CurrentUser.imgUrl = this.urlUserAvartar
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}