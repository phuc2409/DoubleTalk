package com.dualtalk.activity.searchuser

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.CurrentUser
import com.dualtalk.repository.MessageRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchUserViewModel : ViewModel(), Observable {
    sealed class SearchUserState {
        object Success : SearchUserState()
        object Fail : SearchUserState()
        data class GetChatIdSuccess(val chatModel: ChatModel) : SearchUserState()

        object GetChatIdError : SearchUserState()
    }

    var uiState = MutableLiveData<SearchUserState>()
    var mlist = ArrayList<MUser>()
    private val db = Firebase.firestore
    private val messageRepo = MessageRepo()

    fun getListUser() {
        db.collection("users").get().addOnSuccessListener { result ->
            for (user in result) {
                val email = user.getString("email").toString().trim()
                val name = user.getString("fullName").toString().trim()
                val url = user.getString("imgUrl").toString().trim()
                val id = user.getString("id").toString().trim()
                mlist.add(MUser(id, email, name, url))
            }
            uiState.postValue(SearchUserState.Success)
            Log.d("mlist", "Đã có size = ${mlist.size}")
        }
    }

    fun getChatId(user: MUser) {
        val participantIds = arrayListOf(CurrentUser.id, user.id)
        participantIds.sort()
        messageRepo.getChatIdByParticipantIds(participantIds,
            onSuccess = {
                val participantNames = ArrayList<String>()
                val participantImgUrls = ArrayList<String>()
                if (CurrentUser.id == participantIds[0]) {
                    participantNames.add(CurrentUser.fullName)
                    participantImgUrls.add(CurrentUser.imgUrl)
                    user.fullName?.let {
                        participantNames.add(it)
                    } ?: run {
                        participantNames.add("")
                    }
                    user.imgUrl?.let {
                        participantImgUrls.add(it)
                    } ?: run {
                        participantImgUrls.add("")
                    }
                } else {
                    user.fullName?.let {
                        participantNames.add(it)
                    } ?: run {
                        participantNames.add("")
                    }
                    user.imgUrl?.let {
                        participantImgUrls.add(it)
                    } ?: run {
                        participantImgUrls.add("")
                    }
                    participantNames.add(CurrentUser.fullName)
                    participantImgUrls.add(CurrentUser.imgUrl)
                }
                val chatModel =
                    ChatModel(
                        id = it,
                        participantIds,
                        participantNames,
                        participantImgUrls,
                        CurrentUser.id,
                        "",
                        null
                    )
                uiState.postValue(SearchUserState.GetChatIdSuccess(chatModel))
            }, onError = {
                uiState.postValue(SearchUserState.GetChatIdError)
            })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}