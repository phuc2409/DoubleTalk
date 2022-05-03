package com.dualtalk.activity.forgotpassword

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordViewModel : ViewModel() , Observable {
    sealed class forgotPassState{
        object Success : forgotPassState()
        object Fail : forgotPassState()
    }

    val forgotpassState =MutableLiveData<forgotPassState>()

    fun SendLinkToResetPassword(email : String){
        Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener{task->
            if(task.isSuccessful){
                forgotpassState.postValue(forgotPassState.Success)
            }
            else {
                forgotpassState.postValue(forgotPassState.Fail)
            }
        }
    }





    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}