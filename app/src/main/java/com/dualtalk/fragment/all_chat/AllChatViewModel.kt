package com.dualtalk.fragment.all_chat

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.CurrentUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllChatViewModel : ViewModel(), Observable {
    sealed class AllChatState {
        object Update : AllChatState()
    }

    private val database = Firebase.firestore

    val uiState = MutableLiveData<AllChatState>()
    var model = ArrayList<ChatModel>()

    init {
        database.collection("chats").whereArrayContains("participantIds", CurrentUser.id)
            .orderBy("updatedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.e("Message", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val chats = ArrayList<ChatModel>()

                for (i in value!!) {
                    val chat = ChatModel(
                        i.id,
                        i.get("participantIds") as ArrayList<String>,
                        i.get("participantNames") as ArrayList<String>,
                        i.get("participantImgUrls") as ArrayList<String>,
                        i.getString("sendId"),
                        i.getString("sendName"),
                        i.getString("latestMessage"),
                        i.getTimestamp("updatedAt")
                    )
                    chats.add(chat)
                }
                Log.d("All Chats", chats.toString())
                model = chats
                uiState.postValue(AllChatState.Update)
            }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}