package com.dualtalk.fragment.all_chat

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.repository.MessageRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllChatViewModel : ViewModel(), Observable {
    sealed class AllChatState {
        object Update : AllChatState()
    }

    private val messageRepo: MessageRepo = MessageRepo()
    private val database = Firebase.firestore

    val uiState = MutableLiveData<AllChatState>()
    var model = ArrayList<ChatModel>()

    init {
//        messageRepo.createChat("2", "Đạt")
//        messageRepo.createChat("3", "Đạt Nguyễn")
//        messageRepo.createChat("4", "Đạt Feed")
        database.collection("chats").orderBy("updatedAt").addSnapshotListener { value, e ->
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
                    i.getString("latestMessage"),
                    i.getTimestamp("updatedAt")
                )
                Log.d("Chat", chat.toString())
                chats.add(chat)
            }
            Log.d("Chats", chats.toString())
            model = chats
            uiState.postValue(AllChatState.Update)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}