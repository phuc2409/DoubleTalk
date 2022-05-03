package com.dualtalk.activity.chat

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.repository.MessageRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel(), Observable {
    sealed class ChatState {
        object Success : ChatState()
    }

    private val messageRepo: MessageRepo = MessageRepo()
    private val db = Firebase.firestore

    val uiState = MutableLiveData<ChatState>()
    var model = ArrayList<MessageModel>()

    init {
        //todo: filter tin nhắn theo nhận gửi
        //todo: đang cập nhật toàn bộ tin nhắn, chỉ cập nhật tin nhắn mới
        db.collection("messages").orderBy("createdAt").addSnapshotListener { value, e ->
            if (e != null) {
                Log.e("Message", "Listen failed.", e)
                return@addSnapshotListener
            }

            val messages = ArrayList<MessageModel>()

            for (i in value!!) {
                val message = MessageModel(
                    i.id,
                    i.getString("sendId"),
                    i.getString("receiveId"),
                    i.getString("message"),
                    i.getTimestamp("createdAt")
                )
                Log.d("Message", message.toString())
                messages.add(message)
            }
            model = messages
            uiState.postValue(ChatState.Success)
        }
    }

    fun sendMessage(message: String) {
        messageRepo.sendMessage(message)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}