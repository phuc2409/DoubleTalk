package com.dualtalk.activity.login

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(), Observable {

    sealed class LoginState {
        object Loading : LoginState()
        object Success : LoginState()
        object Fail : LoginState()
    }

    val uiLoginState = MutableLiveData<LoginState>()

    fun login(email: String, password: String) {
        val user = UserModel(email, password)
        uiLoginState.postValue(LoginState.Loading)

        if (user.email.equals("dat@gmail.com") && user.password.equals("123456")) {
            uiLoginState.postValue(LoginState.Success)
        } else {
            uiLoginState.postValue(LoginState.Fail)
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}