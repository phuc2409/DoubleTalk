package com.dualtalk.fragment.setting

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.activity.searchuser.MUser

class SettingFragmentViewModel : ViewModel(), Observable {

    sealed class ChooseImageAvatar {
        object Success : ChooseImageAvatar()
        object Fail : ChooseImageAvatar()
        object getinfoSucess : ChooseImageAvatar()
    }

    val uiState = MutableLiveData<ChooseImageAvatar>()

    var infouser = MUser()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}