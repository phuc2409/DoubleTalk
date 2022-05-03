package com.dualtalk.activity.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dualtalk.R
import com.dualtalk.databinding.ActivityChatBinding
import com.google.firebase.FirebaseApp

class ChatActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        dataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

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