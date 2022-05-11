package com.dualtalk.activity.main

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.common.CurrentUser
import com.dualtalk.repository.UserRepo

class MainViewModel : ViewModel(), Observable {
    sealed class MainState {
        object Success : MainState()
    }

    private val userRepo = UserRepo()

    val uiState = MutableLiveData<MainState>()

    init {
        userRepo.getCurrentUser(
            onSuccess = { user ->
                CurrentUser.id = user.id
                user.email?.let {
                    CurrentUser.email = it
                }
                user.fullName?.let {
                    CurrentUser.fullName = it
                }
                user.imgUrl?.let {
                    CurrentUser.imgUrl = it
                }
                uiState.postValue(MainState.Success)
            },
            onError = {

            }
        )
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}