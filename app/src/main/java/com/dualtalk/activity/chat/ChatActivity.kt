package com.dualtalk.activity.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dualtalk.R
import com.dualtalk.common.CurrentUser
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        FirebaseApp.initializeApp(this)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        setupView()
    }

    private fun setupView() {
        val gson = Gson()
        viewModel.chatModel = gson.fromJson(intent.getStringExtra("json"), ChatModel::class.java)
        viewModel.startListener()

        if (viewModel.chatModel.participantNames[0] == CurrentUser.fullName && viewModel.chatModel.participantNames.size > 1) {
            textViewChatName.text = viewModel.chatModel.participantNames[1]
        } else {
            textViewChatName.text = viewModel.chatModel.participantNames[0]
        }

        imgBtnBack.setOnClickListener {
            finish()
        }

        imgBtnSend.isEnabled = false
        imgBtnSend.setOnClickListener {
            viewModel.sendMessage(editTextMessage.text.toString())
        }

        editTextMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                imgBtnSend.isEnabled = p0.toString().isNotBlank()
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.uiState.observe(this) {
            if (it == ChatViewModel.ChatState.SendSuccess) {
                editTextMessage.text.clear()

                val messageAdapter = MessageAdapter(viewModel.model)
                recyclerView.adapter = messageAdapter
                recyclerView.scrollToPosition(viewModel.model.size - 1)
            }
            else if (it == ChatViewModel.ChatState.ReceiveSuccess) {
                val messageAdapter = MessageAdapter(viewModel.model)
                recyclerView.adapter = messageAdapter
                recyclerView.scrollToPosition(viewModel.model.size - 1)
            }
        }
    }
}