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
    lateinit var chatModel: ChatModel
    var model = ArrayList<MessageModel>()

    fun sendMessage(message: String) {
        messageRepo.sendMessage(chatModel, message)
    }

    fun startListener() {
        //todo: đang cập nhật toàn bộ tin nhắn, chỉ cập nhật tin nhắn mới
        db.collection("messages").orderBy("createdAt").whereEqualTo("chatId", chatModel.id)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.e("Message", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val messages = ArrayList<MessageModel>()

                for (i in value!!) {
                    val message = MessageModel(
                        i.id,
                        i.getString("chatId"),
                        i.getString("sendId"),
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

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}