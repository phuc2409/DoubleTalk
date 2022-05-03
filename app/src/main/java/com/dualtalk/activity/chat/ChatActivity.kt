package com.dualtalk.activity.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dualtalk.R
import com.dualtalk.common.Constant
import com.dualtalk.databinding.ActivityChatBinding
import com.google.firebase.FirebaseApp
import com.google.gson.Gson

class ChatActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        dataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val gson = Gson()
        viewModel.chatModel = gson.fromJson(intent.getStringExtra("json"), ChatModel::class.java)
        viewModel.startListener()

        if (viewModel.chatModel.participantNames[0] == Constant.sendName && viewModel.chatModel.participantNames.size > 1) {
            dataBinding.textViewChatName.text = viewModel.chatModel.participantNames[1]
        } else {
            dataBinding.textViewChatName.text = viewModel.chatModel.participantNames[0]
        }

        dataBinding.buttonSend.setOnClickListener {
            viewModel.sendMessage(dataBinding.editTextMessage.text.toString())
        }

        dataBinding.editTextMessage.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dataBinding.buttonSend.isEnabled = p0.toString() != ""
            }
        })

        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        dataBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.uiState.observe(this) {
            if (it == ChatViewModel.ChatState.Success) {
                dataBinding.editTextMessage.text.clear()

                val messageAdapter = MessageAdapter(viewModel.model)
                dataBinding.recyclerView.adapter = messageAdapter
                dataBinding.recyclerView.scrollToPosition(viewModel.model.size - 1)
            }
        }
    }
}