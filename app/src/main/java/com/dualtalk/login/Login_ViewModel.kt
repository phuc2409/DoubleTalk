package com.dualtalk.login

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Login_ViewModel : ViewModel() , Observable{

    sealed class LoginState{
        object Loading : LoginState()
        object Success : LoginState()
        object Fail : LoginState()
    }

    val UiLoginState = MutableLiveData<LoginState>()

    fun login(email:String , password:String){
        val user = User(email , password)
        UiLoginState.postValue(LoginState.Loading)

        if(user.email.equals("dat@gmail.com") && user.password.equals("123456")){
            UiLoginState.postValue(LoginState.Success)
        }

        else {
            UiLoginState.postValue(LoginState.Fail)
        }
    }




    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

}