package com.dualtalk.activity.editinfouser

import android.net.Uri
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.common.CurrentUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditInfoUserViewModel : ViewModel() , Observable {
    sealed class UpdateInfoState{
        object Success : UpdateInfoState()
        object Fail : UpdateInfoState()
    }
    val updateInfoState = MutableLiveData<UpdateInfoState>()
    val db = Firebase.firestore




    fun UpdateInfoUser(name : String){
        val sfDocRef = db.collection("users").document(CurrentUser.id)
        db.runTransaction {transaction->
            val snapshot = transaction.get(sfDocRef)
            transaction.update(sfDocRef , "fullName" , name)

        }.addOnSuccessListener {
            updateInfoState.postValue(UpdateInfoState.Success)
        }.addOnFailureListener {
            updateInfoState.postValue(UpdateInfoState.Fail)
        }
    }






    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}