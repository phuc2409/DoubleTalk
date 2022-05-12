package com.dualtalk.activity.chat

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dualtalk.common.CurrentUser
import com.dualtalk.repository.MessageRepo
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel(), Observable {
    sealed class ChatState {
        object SendSuccess : ChatState()
        object ReceiveSuccess : ChatState()
    }

    private val messageRepo: MessageRepo = MessageRepo()
    private val db = Firebase.firestore
    private lateinit var listenerRegistration: ListenerRegistration

    val uiState = MutableLiveData<ChatState>()
    lateinit var chatModel: ChatModel
    var model = ArrayList<MessageModel>()

    fun sendMessage(message: String) {
        if (chatModel.id == "") {
            //Gửi tin nhắn đầu tiên
            messageRepo.createChat(chatModel,
                onSuccess = {
                    chatModel.id = it
                    removeListener()
                    startListener()
                    messageRepo.sendMessage(chatModel, message)
                }, onError = {

                })
        } else {
            messageRepo.sendMessage(chatModel, message)
        }
    }

    fun startListener() {
        //todo: đang cập nhật toàn bộ tin nhắn, chỉ cập nhật tin nhắn mới
        listenerRegistration =
            db.collection("messages").whereEqualTo("chatId", chatModel.id).orderBy("createdAt")
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
                    if (model.isNotEmpty()) {
                        if (model[model.size - 1].sendId == CurrentUser.id) {
                            uiState.postValue(ChatState.SendSuccess)
                        } else {
                            uiState.postValue(ChatState.ReceiveSuccess)
                        }
                    } else {
                        uiState.postValue(ChatState.ReceiveSuccess)
                    }
                }
    }

    private fun removeListener() {
        listenerRegistration.remove()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}